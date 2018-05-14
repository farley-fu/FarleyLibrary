package com.xiao.mo.ji.xiaomoji.mvp

class ActivityDelegateImp<P : Mvppresenter<MvpView>, V : MvpView> : ActyvityDelegate {
    private val basemvpDelegateCallback: BasemvpDelegateCallback<P, V>
    private val mvpInternalDelegate: MvpInternalDelegate<P, V>

    constructor(basemvpDelegateCallback: BasemvpDelegateCallback<P, V>) {
        if (basemvpDelegateCallback == null)
            throw NullPointerException("the basemvpDelegateCallback in  ActivityDelegateImpn is null")
        this.basemvpDelegateCallback = basemvpDelegateCallback
        mvpInternalDelegate = MvpInternalDelegate(this.basemvpDelegateCallback)
    }

    override fun onCreate() {
        mvpInternalDelegate.creatPresneter()
        mvpInternalDelegate.attachView()
    }

    override fun onPause() {
    }

    override fun onResume() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
        mvpInternalDelegate.detacthView()
    }
}