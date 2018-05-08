package com.xiao.mo.ji.xiaomoji

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.example.farleylibrary.loadingdialog.DialogUtils
import com.example.farleylibrary.loadingdialog.MessageEvent
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus

class MainActivity: Activity(), Runnable {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed(this@MainActivity,2000)
        dialogShow.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                DialogUtils.showLoadingDialog(this@MainActivity)
            }
        })
    }
    var times = 0
    override fun run() {
        // 发布事件
        times++
        EventBus.getDefault().post( MessageEvent("Hello EventBus!"+times));
        Handler().postDelayed(this@MainActivity,2000)
    }
}
