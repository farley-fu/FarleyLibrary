package com.xiao.mo.ji.xiaomoji.mvp

class FragmentDelefatelmp<P : Mvppresenter<MvpView>, V : MvpView> : FragmentDelegate {
    private val basemvpDelegateCallback: BasemvpDelegateCallback<P, V>
    private var mvpInternalDelegate: MvpInternalDelegate<P, V>

    constructor (basemvpDelegateCallback: BasemvpDelegateCallback<P, V>?) {
        if (basemvpDelegateCallback == null)
            throw NullPointerException("the basemvpDelegateCallback in  ActivityDelegateImpn is null")
        this.basemvpDelegateCallback = basemvpDelegateCallback
        mvpInternalDelegate = MvpInternalDelegate(this.basemvpDelegateCallback)
    }

    override fun onCreate() {
        mvpInternalDelegate.creatPresneter()
        mvpInternalDelegate.attachView()
    }

    override fun onDestroy() {

    }
}