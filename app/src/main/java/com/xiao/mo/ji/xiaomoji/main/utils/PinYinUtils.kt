package com.xiao.mo.ji.xiaomoji.main.utils

import net.sourceforge.pinyin4j.PinyinHelper
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType
import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.util.Log
import com.xiao.mo.ji.xiaomoji.main.bean.ContactsBean


/*
 * 描述:     得到指定汉字的拼音
 */
object PinYinUtils {
    /**
     * 将hanzi转成拼音
     *
     * @param hanzi 汉字或字母
     * @return 拼音
     */
    fun getPinyin(hanzi: String): String {
        val sb = StringBuilder()
        val format = HanyuPinyinOutputFormat()
        format.caseType = HanyuPinyinCaseType.UPPERCASE
        format.toneType = HanyuPinyinToneType.WITHOUT_TONE
        //由于不能直接对多个汉子转换，只能对单个汉子转换
        val arr = hanzi.toCharArray()
        for (i in arr.indices) {
            if (Character.isWhitespace(arr[i])) {
                continue
            }
            try {
                val pinyinArr = PinyinHelper.toHanyuPinyinStringArray(arr[i], format)
                if (pinyinArr != null) {
                    sb.append(pinyinArr[0])
                } else {
                    sb.append(arr[i])
                }
            } catch (e: Exception) {
                e.printStackTrace()
                //不是正确的汉字
                sb.append(arr[i])
            }

        }
        return sb.toString()
    }

    private val PHONES_PROJECTION = arrayOf(Phone.DISPLAY_NAME, Phone.NUMBER)
    fun getPhoneContacts(mContext: Context): ArrayList<ContactsBean> {
        Log.e("farley","getPhoneContacts")
        val infos = ArrayList<ContactsBean>()
        val cursor = mContext.contentResolver.query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null)
        if (cursor != null) {

            while (cursor.moveToNext()) {


                val contactName = cursor.getString(0)
                val phoneNumber = cursor.getString(1)
                var info: ContactsBean? = ContactsBean(contactName,phoneNumber)
                Log.e("farley","contactName:"+contactName+";phoneNumber:"+phoneNumber)
                infos.add(info!!)
                info = null
            }
            cursor.close()
        }else{
            Log.e("farley","null")
        }
        return infos
    }
}
