package com.example.farleylibrary.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.v4.content.FileProvider
import com.example.farleylibrary.R
import com.google.gson.Gson
import java.io.File
import java.io.IOException
import com.squareup.okhttp.Call
import com.squareup.okhttp.Callback
import com.squareup.okhttp.FormEncodingBuilder
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import com.squareup.okhttp.Response

class PyUtils {
    private var activity: Activity

    constructor(activity: Activity) {
        this.activity = activity
    }

    //    static String checkurl="http://www.pgyer.com/apiv1/app/viewGroup";
    internal var checkurl = "https://www.pgyer.com/apiv2/app/check"
    internal var downUrl = "https://www.pgyer.com/apiv2/app/check"

    fun getVersion(): Int {
        val pkg: PackageInfo
        var versionCode = 0
        var versionName = ""
        try {
            pkg = activity.packageManager.getPackageInfo(activity.application.packageName, 0)
            val appName = pkg.applicationInfo.loadLabel(activity.packageManager).toString()
            versionName = pkg.versionName
            println("appName:$appName")
            println("versionName:$versionName")
            versionCode = pkg.versionCode

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return versionCode
    }

    fun check() {
        //当所用app前版本号
        val codeversin = getVersion()
        getLineVersion(checkurl, codeversin)
    }

    fun getLineVersion(url: String, nowcodeversin: Int) {
        var okHttpClient = OkHttpClient()
        var formBody = FormEncodingBuilder()
                .add("_api_key", AutoUpdate._api_key)
                .add("appKey", AutoUpdate.APPID)
                .build()
        var request = Request.Builder()
                .url(url)
                .post(formBody)
                .build()
        var call = okHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(request: Request?, e: IOException?) {
                LogUtils.e("request="+request.toString())
            }

            override fun onResponse(response: Response?) {
                val result = response!!.body().string()
                val gson = Gson()
                val bean = gson.fromJson(result, PyVersionBean::class.java) ?: return
                if (bean.data == null) {
                    return
                }
                if (bean.data!!.buildVersionNo == null) {
                    return
                }
                val lineVersion = Integer.parseInt(bean.data!!.buildVersionNo )
                LogUtils.e("lineVersion=$lineVersion；$nowcodeversin")
                if (lineVersion > nowcodeversin) {
                    if (checkListener != null) {
                        checkListener!!.checkSuccess()
                        downUrl = bean.data!!.downloadURL!!
                    }
                }
            }


        })
    }

    fun downUrl() {
        showLoading()
        DownloadUtil.get().download(downUrl, activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.absolutePath, object : DownloadUtil.OnDownloadListener {
            override fun onDownloadSuccess(file: File) {
                if (pd6 != null) {
                    pd6!!.dismiss()
                }
                LogUtils.e("下载完成去安装")
                installAPK(file)
            }

            override fun onDownloading(progress: Int) {
                if (pd6 != null) {
                    pd6!!.progress = progress
                }

            }

            override fun onDownloadFailed() {
                LogUtils.e("下载失败")
                if (pd6 != null) {
                    pd6!!.dismiss()
                }
                if (checkListener != null) {
                    checkListener!!.checkSuccess()
                }
            }
        })

    }

    /**
     * 提升读写权限
     * @param filePath 文件路径
     * @return
     * @throws IOException
     */
    fun setPermission(filePath: String) {
        val command = "chmod 777 $filePath"
        val runtime = Runtime.getRuntime()
        try {
            runtime.exec(command)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    ////调用系统的安装方法
    private fun installAPK(savedFile: File) {
        //调用系统的安装方法
        val intent = Intent(Intent.ACTION_VIEW)
        val data: Uri
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
            data = FileProvider.getUriForFile(activity, "com.xxx.xxx.fileprovider", savedFile)
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            LogUtils.e("7.0data=$data")
        } else {
            data = Uri.fromFile(savedFile)
            LogUtils.e("111data=$data")
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive")
        activity.startActivity(intent)
        activity.finish()
    }

    private var pd6: ProgressDialog? = null

    fun showLoading() {
        pd6 = ProgressDialog(activity)
        pd6!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)// 设置水平进度条
        pd6!!.setCancelable(false)// 设置是否可以通过点击Back键取消
        pd6!!.setCanceledOnTouchOutside(false)// 设置在点击Dialog外是否取消Dialog进度条
        //        pd6.setIcon(R.mipmap.ic_launcher);// 设置提示的title的图标，默认是没有的
        pd6!!.setTitle(activity.getString(R.string.update_tips))
        pd6!!.max = 100
        pd6!!.setMessage(activity.getString(R.string.downloading))
        pd6!!.show()
    }

    private var checkListener: CheckListener? = null

    interface CheckListener {
        fun checkSuccess()
    }

    fun setCheckListener(checkListener: CheckListener) {
        this.checkListener = checkListener
    }
}