package com.xiao.mo.ji.xiaomoji.main.bean

import com.xiao.mo.ji.xiaomoji.main.utils.PinYinUtils

/*
 * 描述:       封装联系人列表信息
 */


class Person(name:String){
    //拼音
    var pinyin: String
    //拼音
    var name: String
    //拼音首字母
    var headerWord: String

    init {
        this.pinyin = PinYinUtils.getPinyin(name)
        this.name = name
        headerWord = pinyin.substring(0, 1)
    }
}
