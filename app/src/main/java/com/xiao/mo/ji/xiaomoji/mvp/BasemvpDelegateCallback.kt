package com.xiao.mo.ji.xiaomoji.mvp

interface BasemvpDelegateCallback<P : Mvppresenter<MvpView>,V : MvpView> {
    abstract fun setPresenter()
    abstract fun getPresenter(): P?
    abstract fun createPresenter(): P?
    abstract fun getMvpView(): V
}