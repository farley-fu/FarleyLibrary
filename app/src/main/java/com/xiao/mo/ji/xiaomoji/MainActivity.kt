package com.xiao.mo.ji.xiaomoji

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.example.farleylibrary.loadingdialog.DialogUtils
import com.example.farleylibrary.loadingdialog.LoadingDialog
import com.example.farleylibrary.loadingdialog.MessageEvent
import com.example.farleylibrary.utils.LogUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus

class MainActivity: Activity(), Runnable {

    var dialog :LoadingDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LogUtils.canShow = false
        Handler().postDelayed(this@MainActivity,2000)
        dialogShow.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                dialog = DialogUtils.showLoadingDialog(this@MainActivity)
                LogUtils.d("open")
            }
        })
        dialogHide.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                DialogUtils.closeDialog(dialog)
                LogUtils.d("close")
            }
        })

    }
    var times = 0
    override fun run() {
        // 发布事件
        times++
        if (dialog != null && dialog!!.isShowing()) {
            EventBus.getDefault().post(MessageEvent("Hello EventBus!" + times));
        }
            Handler().postDelayed(this@MainActivity, 2000)

    }
}
