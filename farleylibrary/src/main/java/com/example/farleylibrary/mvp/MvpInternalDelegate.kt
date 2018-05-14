package com.xiao.mo.ji.xiaomoji.mvp

class MvpInternalDelegate<P : Mvppresenter<MvpView>, V : MvpView>  {
    internal var callback: BasemvpDelegateCallback<P, V>
    constructor(callback:BasemvpDelegateCallback<P, V>) {
        this.callback = callback
    }
    fun creatPresneter(): P {
        var p = callback.getPresenter()
        if (p == null)
            p = callback.createPresenter()
        if (p == null)
            throw NullPointerException("callback.createPresenter() is null in MvpInternalDelegate")
        return p
    }

    fun attachView() {
        callback.getPresenter()!!.attachView(callback.getMvpView())
    }

    fun detacthView() {
        callback.getPresenter()!!.detachView()
    }
}