package com.xiao.mo.ji.xiaomoji.mvp

import android.app.Fragment
import android.os.Bundle

abstract class MvpFragment<P : Mvppresenter<MvpView>, V : MvpView> : Fragment(),
ActivityMvpDelegateCallback<P, V>, MvpView {
    private var fragmentDelegate: FragmentDelegate? = null
    private var mPresenter: P? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragmentDelegate = FragmentDelefatelmp(this)
        fragmentDelegate!!.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentDelegate!!.onDestroy()
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