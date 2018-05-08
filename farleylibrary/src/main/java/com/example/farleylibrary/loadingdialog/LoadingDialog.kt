package com.example.farleylibrary.loadingdialog

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.example.farleylibrary.R
import kotlinx.android.synthetic.main.dialog_loading.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class LoadingDialog : AlertDialog {
    constructor(context: Context) : super(context, R.style.myDialog) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)
        // 注册订阅者
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        Log.i("farley", "message is " + event.message)
        // 更新界面
        tipTextView.setText(event.message)
    }
    override fun onStop() {
        super.onStop()
        // 注销订阅者
        EventBus.getDefault().unregister(this);
    }

}