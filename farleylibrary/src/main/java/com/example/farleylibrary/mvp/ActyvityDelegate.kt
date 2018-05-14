package com.xiao.mo.ji.xiaomoji.mvp

interface ActyvityDelegate {
    abstract fun onCreate()
    abstract fun onPause()
    abstract fun onResume()
    abstract fun onStop()
    abstract fun onDestroy()
}