package com.example.farleylibrary.utils

import android.util.Log

object LogUtils {
    var canShow = true
    fun e(msg:String){
        if (canShow) {
            Log.e("farley", msg)
        }else{
            Log.e("farley", "msg is closed")
        }
    }
    fun d(msg:String){
        if (canShow) {
            Log.d("farley", msg)
        }else{
            Log.d("farley", "msg is closed")
        }
    }
    fun i(msg:String){
        if (canShow) {
            Log.i("farley", msg)
        }else{
            Log.i("farley", "msg is closed")
        }
    }
}