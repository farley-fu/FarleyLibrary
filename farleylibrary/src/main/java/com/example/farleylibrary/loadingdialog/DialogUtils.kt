package com.example.farleylibrary.loadingdialog

import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import com.example.farleylibrary.R


object DialogUtils {
   fun showLoadingDialog(context: Context){
       var dialog = LoadingDialog(context)
       dialog.setCancelable(true) // 是否可以按“返回键”消失
       dialog.setCanceledOnTouchOutside(false) // 点击加载框以外的区域
       /**
        *将显示Dialog的方法封装在这里面
        */
       val window = dialog.getWindow()
       val lp = window.getAttributes()
       lp.width = WindowManager.LayoutParams.MATCH_PARENT
       lp.height = WindowManager.LayoutParams.WRAP_CONTENT
       window.setGravity(Gravity.CENTER)
       window.setAttributes(lp)
       window.setWindowAnimations(R.style.PopWindowAnimStyle)
       dialog.show()
   }
}