package com.example.farleylibrary.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Handler
import com.example.farleylibrary.R

object AutoUpdate {
    var _api_key = "b38fe152743f3459cb999e246d10ebb1"
    var APPID = "5c25f8cb2a653a2f5df391b496a7f517"
    //自动更新
    fun canUpdata(activity: Activity) {
        Handler().postDelayed({
            val pyUtils = PyUtils(activity)
            pyUtils.setCheckListener(object : PyUtils.CheckListener {
                override fun checkSuccess() {
                    activity.runOnUiThread {
                        val dialog = AlertDialog.Builder(activity)
                                .setTitle(activity.getString(R.string.update_title))
                                .setMessage(activity.getString(R.string.update_desc))
                                .setNegativeButton(activity.getString(R.string.update_sure)) { dialog, which -> pyUtils.downUrl() }.show()
                        dialog.setCanceledOnTouchOutside(false)
                        dialog.setCancelable(false)
                    }
                }
            })
            pyUtils.check()
        }, 1000)
    }
}