package com.xiao.mo.ji.xiaomoji.mvp

import android.os.Bundle
import android.support.v4.app.FragmentActivity

abstract class MvpFragmentActivity<P : Mvppresenter<MvpView>, V : MvpView> : FragmentActivity(),
ActivityMvpDelegateCallback<P, V>, MvpView  {
    private var mActyvityDelegate: ActyvityDelegate? = null
    private var mPresenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActyvityDelegate = ActivityDelegateImp(this)
        mActyvityDelegate!!.onCreate()

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