package com.xiao.mo.ji.xiaomoji.main.adapter

/*
 * 描述:       ListView列表适配器
 */

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.xiao.mo.ji.xiaomoji.R
import com.xiao.mo.ji.xiaomoji.main.bean.Person

class MyAdapter(context: Context, private val list: List<Person>) : BaseAdapter() {
    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder
        if (convertView == null) {
            holder = ViewHolder()
            convertView = inflater.inflate(R.layout.list_item, null)
            holder.tv_word = convertView!!.findViewById(R.id.tv_word) as TextView
            holder.tv_name = convertView.findViewById(R.id.tv_name) as TextView
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        val word = list[position].headerWord
        holder.tv_word!!.text = word
        holder.tv_name!!.text = list[position].name
        //将相同字母开头的合并在一起
        if (position == 0) {
            //第一个是一定显示的
            holder.tv_word!!.visibility = View.VISIBLE
        } else {
            //后一个与前一个对比,判断首字母是否相同，相同则隐藏
            val headerWord = list[position - 1].headerWord
            if (word == headerWord) {
                holder.tv_word!!.visibility = View.GONE
            } else {
                holder.tv_word!!.visibility = View.VISIBLE
            }
        }
        return convertView
    }

    private inner class ViewHolder {
         var tv_word: TextView? = null
         var tv_name: TextView? = null
    }
}
