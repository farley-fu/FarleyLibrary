package com.xiao.mo.ji.xiaomoji.mvp

import android.app.Activity
import android.os.Bundle

abstract class MvpActivity<P : Mvppresenter<MvpView>, V : MvpView> : Activity ,ActivityMvpDelegateCallback<P, V>, MvpView {
    private var mActyvityDelegate: ActyvityDelegate? = null
    private var mPresenter: P? = null
    constructor(){

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActyvityDelegate = ActivityDelegateImp(this)
        mActyvityDelegate!!.onCreate()
        ControllerActivity.addActivity(this)
    }

    override fun onResume() {
        super.onResume()
        mActyvityDelegate!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mActyvityDelegate!!.onPause()
    }


    override fun onStop() {
        super.onStop()
        mActyvityDelegate!!.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mActyvityDelegate!!.onDestroy()
        ControllerActivity.removeActivity(this)
    }

    override fun setPresenter() {

    }

    protected abstract fun CreatePresenter(): P //暴露一个创建的方法用于创建presenter

    override fun createPresenter()//这个方法由MvpInternalDelegate 调用BasemvpDelegateCallback 来创建presenter
            : P? {
        mPresenter = CreatePresenter()
        return mPresenter
    }

    override fun getPresenter(): P? {//
        return mPresenter
    }

    override fun getMvpView(): V {
        return this as V
    }

}