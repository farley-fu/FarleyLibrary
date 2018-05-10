package com.xiao.mo.ji.xiaomoji.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.*
import com.example.farleylibrary.loadingdialog.DialogUtils
import com.xiao.mo.ji.xiaomoji.R
import com.xiao.mo.ji.xiaomoji.main.view.WordsNavigation
import com.xiao.mo.ji.xiaomoji.mvp.MvpActivity
import com.xiao.mo.ji.xiaomoji.mvp.MvpView
import com.xiao.mo.ji.xiaomoji.main.bean.Person
import com.xiao.mo.ji.xiaomoji.main.adapter.MyAdapter
import com.xiao.mo.ji.xiaomoji.main.utils.PinYinUtils
import java.util.*
import kotlinx.android.synthetic.main.activity_contacts_list.*


class ContactsListActivity:MvpActivity<ContactsListPresenter,MvpView>(),MvpView , WordsNavigation.onWordsChangeListener, AbsListView.OnScrollListener, AdapterView.OnItemClickListener {


    var myPresenter: ContactsListPresenter?=null
    override fun CreatePresenter(): ContactsListPresenter {
         myPresenter = ContactsListPresenter(this)
        return myPresenter as ContactsListPresenter;
    }

    private var handler: Handler? = null
    private var list: List<Person>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_list)
        //初始化数据
        initData()
        //初始化列表
        initListView()

        //设置列表点击滑动监听
        handler = Handler()
        wordNav.setOnWordsChangeListener(this)
        getPhoneData()

    }

    private fun getPhoneData() {
        var string_array:Array<String> = arrayOf(Manifest.permission.WRITE_CONTACTS)
        if (ContextCompat.checkSelfPermission(this@ContactsListActivity,
                        Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@ContactsListActivity,  string_array, 1);
        } else {
            PinYinUtils.getPhoneContacts(this)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                PinYinUtils.getPhoneContacts(this)
            } else {
                Toast.makeText(this, "你拒绝了这个权限", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun initListView() {
        val adapter = MyAdapter(this, list!!)
        listView!!.setAdapter(adapter)
        listView!!.setOnScrollListener(this)
        listView!!.setOnItemClickListener(this)
    }
    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var dialog = DialogUtils.showLoadingDialog(this@ContactsListActivity)
    }
    /**
     * 初始化联系人列表信息
     */
    private fun initData() {
        list = ArrayList()
        val p1 = Person("Dave")
        val p2 = Person("张晓飞")
        val p3 = Person("杨光福")
        val p4 = Person("阿钟")
        val p5 = Person("胡继群")
        val p6 = Person("徐歌阳")
        val p7 = Person("钟泽兴")
        val p8 = Person("宋某人")
        val p9 = Person("刘某人")
        val p10 = Person("Tony")
        val p11 = Person("老刘")
        val p12 = Person("隔壁老王")
        val p13 = Person("安传鑫")
        val p14 = Person("温松")
        val p15 = Person("成龙")
        val p16 = Person("那英")
        val p17 = Person("刘甫")
        val p18 = Person("沙宝亮")
        val p19 = Person("张猛")
        val p20 = Person("张大爷")
        val p21 = Person("张哥")
        val p22 = Person("张娃子")
        val p23 = Person("樟脑丸")
        val p24 = Person("吴亮")
        val p25 = Person("Tom")
        val p26 = Person("阿三")
        val p27 = Person("肖奈")
        val p28 = Person("贝微微")
        val p29 = Person("赵二喜")
        val p30 = Person("曹光")
        val p31 = Person("姜宇航")
        list = listOf(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16,p17,p18,p19,p20,p21,p22,p23,p24,p25,p26,p27,p28,p29,p30,p31)
        //对集合排序
        Collections.sort(list, object : Comparator<Person> {
            override fun compare(lhs: Person, rhs: Person): Int {
                //根据拼音进行排序
                return lhs.pinyin.compareTo(rhs.pinyin)
            }
        })
    }

    //手指按下字母改变监听回调
    override fun wordsChange(words: String) {
        updateWord(words)
        updateListView(words)
    }

    override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {

    }

    override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
        //当滑动列表的时候，更新右侧字母列表的选中状态
        wordNav!!.setTouchIndex(list!!.get(firstVisibleItem).headerWord)
    }

    /**
     * @param words 首字母
     */
    private fun updateListView(words: String) {
        for (i in 0 until list!!.size) {
            val headerWord = list!!.get(i).headerWord
            //将手指按下的字母与列表中相同字母开头的项找出来
            if (words == headerWord) {
                //将列表选中哪一个
                listView!!.setSelection(i)
                //找到开头的一个即可
                return
            }
        }
    }

    /**
     * 更新中央的字母提示
     *
     * @param words 首字母
     */
    private fun updateWord(words: String) {
        tv!!.setText(words)
        tv!!.setVisibility(View.VISIBLE)
        //清空之前的所有消息
        handler!!.removeCallbacksAndMessages(null)
        //1s后让tv隐藏
        handler!!.postDelayed(Runnable { tv!!.setVisibility(View.GONE) }, 500)
    }

}