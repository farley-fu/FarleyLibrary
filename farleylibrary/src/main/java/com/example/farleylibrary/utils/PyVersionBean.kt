package com.example.farleylibrary.utils

import com.google.gson.annotations.Expose

class PyVersionBean {
    /**
     * code : 0
     * message :
     * data : {"buildBuildVersion":"37","downloadURL":"https://www.pgyer.com/app/installUpdate/abaf1e25ceff27f15569a89a6d196ad4?sig=93c8Nme06UNMu8sQscr8ae2XGNBM9kZ5oOsZdH6xdwOjc4vT2P9qX8v6RLVWyE13","buildVersionNo":"22","buildVersion":"1.0.22","buildShortcutUrl":"https://www.pgyer.com/g08R","buildUpdateDescription":""}
     */
    @Expose
    var code: Int = 0
    @Expose
    var message: String? = null
    @Expose
    var data: DataBean? = null


    class DataBean {
        /**
         * buildBuildVersion : 37
         * downloadURL : https://www.pgyer.com/app/installUpdate/abaf1e25ceff27f15569a89a6d196ad4?sig=93c8Nme06UNMu8sQscr8ae2XGNBM9kZ5oOsZdH6xdwOjc4vT2P9qX8v6RLVWyE13
         * buildVersionNo : 22
         * buildVersion : 1.0.22
         * buildShortcutUrl : https://www.pgyer.com/g08R
         * buildUpdateDescription :
         */
        @Expose
        var buildBuildVersion: String? = null
        @Expose
        var downloadURL: String? = null
        @Expose
        var buildVersionNo: String? = null
        @Expose
        var buildVersion: String? = null
        @Expose
        var buildShortcutUrl: String? = null
        @Expose
        var buildUpdateDescription: String? = null
    }
}