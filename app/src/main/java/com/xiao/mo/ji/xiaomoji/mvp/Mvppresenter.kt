package com.xiao.mo.ji.xiaomoji.mvp

interface Mvppresenter<V : MvpView>  {
    //添加view
    abstract fun attachView(view: V)

    //为防止内存泄露，要及时取消关联
    abstract fun detachView()
}