package com.example.farleylibrary.utils

import android.os.Environment
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class DownloadUtil {

    private var okHttpClient: OkHttpClient? = null
    companion object {
        private var downloadUtil: DownloadUtil? = null
        fun get(): DownloadUtil {
            if (downloadUtil == null) {
                downloadUtil = DownloadUtil()
            }
            return downloadUtil as DownloadUtil
        }
    }


    constructor() {
        okHttpClient = OkHttpClient()
    }

    /**
     * @param url 下载连接
     * @param saveDir 储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    fun download(url: String, saveDir: String, listener: OnDownloadListener) {
        LogUtils.e("downpath=$saveDir")
        val request = Request.Builder().url(url).build()
        okHttpClient!!.newCall(request).enqueue(object : Callback {
            override fun onFailure(request: Request, e: IOException) {
                // 下载失败
                listener.onDownloadFailed()
                LogUtils.e("下载失败，原因=" + e.message)
            }

            @Throws(IOException::class)
            override fun onResponse(response: Response) {
                var inputStr: InputStream? = null
                val buf = ByteArray(2048)
                var len = 0
                var fos: FileOutputStream? = null
                // 储存下载文件的目录
                val savePath = isExistDir(saveDir)
                try {
                    inputStr = response.body().byteStream()
                    val total = response.body().contentLength()
                    val file = File(savePath, getNameFromUrl(url))
                    LogUtils.e("downpath1=" + file.absolutePath)
                    fos = FileOutputStream(file)
                    var sum: Long = 0
                    var len = inputStr.read(buf)
                    LogUtils.e("len=" + len)
                    while (len != -1) {
                        fos.write(buf, 0, len)
                        sum += len.toLong()
                        val progress = (sum * 1.0f / total * 100).toInt()
                        // 下载中
                        listener.onDownloading(progress)
                        len = inputStr.read(buf)
                        LogUtils.e("len-while=" + len)
                    }
                    fos.flush()
                    LogUtils.e("=提升权限=")
                    //提升权限
                    val command = "chmod " + "777" + " " + file.absolutePath
                    val runtime = Runtime.getRuntime()
                    try {
                        runtime.exec(command)
                        LogUtils.e("提升权限")
                        // 下载完成
                        listener.onDownloadSuccess(file)
                    } catch (e: IOException) {
                        LogUtils.e("提升权限失败" + e.message)
                        e.printStackTrace()
                    }

                } catch (e: Exception) {
                    listener.onDownloadFailed()
                    LogUtils.e("下载失败，原因=" + e.message)
                } finally {
                    try {
                        if (inputStr != null)
                            inputStr.close()
                    } catch (e: IOException) {
                    }

                    try {
                        if (fos != null)
                            fos.close()
                    } catch (e: IOException) {
                    }

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
            LogUtils.e("提升权限")
        } catch (e: IOException) {
            LogUtils.e("提升权限失败" + e.message)
            e.printStackTrace()
        }

    }

    /**
     * @param saveDir
     * @return
     * @throws IOException
     * 判断下载目录是否存在
     */
    @Throws(IOException::class)
    private fun isExistDir(saveDir: String): String {
        // 下载位置
        val downloadFile = File(Environment.getExternalStorageDirectory(), saveDir)
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile()
        }
        val savePath = downloadFile.absolutePath
        return downloadFile.absolutePath
    }

    /**
     * @param url
     * @return
     * 从下载连接中解析出文件名
     */
    private fun getNameFromUrl(url: String): String {
        //        return url.substring(url.lastIndexOf("/") + 1);
        return "updata.apk"
    }

    interface OnDownloadListener {
        /**
         * 下载成功
         * @param file
         */
        fun onDownloadSuccess(file: File)

        /**
         * @param progress
         * 下载进度
         */
        fun onDownloading(progress: Int)

        /**
         * 下载失败
         */
        fun onDownloadFailed()
    }
}