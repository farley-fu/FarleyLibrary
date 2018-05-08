package com.xiao.mo.ji.xiaomoji.mvp

import android.app.Activity
import java.util.ArrayList

class ControllerActivity {


    companion object {
        var activities: MutableList<Activity> = ArrayList()
        // 包裹范围内 属于静态方法
        fun addActivity(activity: Activity) {
            if (!activities.contains(activity)) {
                activities.add(activity)
            }
        }

        fun removeActivity(activity: Activity) {
            if (activities.contains(activity)) {
                activities.remove(activity)
                activity.finish()
            }
        }

        fun finishAll() {
            for (activity in activities) {
                activity.finish()
            }
        }
    }

}