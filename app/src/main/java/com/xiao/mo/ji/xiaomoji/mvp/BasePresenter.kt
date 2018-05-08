package com.xiao.mo.ji.xiaomoji.mvp

import java.lang.ref.WeakReference

open class BasePresenter<V : MvpView> : Mvppresenter<V>  {
    private var reference: WeakReference<V>? = null
    override fun attachView(view: V) {
        if (view == null)
            throw NullPointerException("view can not be null when in attachview() in MvpBasepresenter")
        else {
            if (reference == null)
                reference = WeakReference(view)

        }
    }
   override fun detachView() {

        if (reference != null)
            reference!!.clear()
    }

    fun getMvpView(): V {
        return if (reference != null && reference!!.get() != null)
            reference!!.get()!!
        else
            throw NullPointerException("have you ever called attachview() in MvpBasepresenter")
    }

    fun isAttach(): Boolean? {
        return reference != null && reference!!.get() != null
    }



}