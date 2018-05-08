package com.xiao.mo.ji.xiaomoji.main.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/*
 * 描述:     实现手机联系人列表导航
 */
class WordsNavigation : View {

    /*绘制的列表导航字母*/
    private val words = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#")
    /*字母画笔*/
    private var wordsPaint: Paint? = null
    /*字母背景画笔*/
    private var bgPaint: Paint? = null
    /*每一个字母的宽度*/
    private var itemWidth: Int = 0
    /*每一个字母的高度*/
    private var itemHeight: Int = 0
    /*手指按下的字母索引*/
    private var touchIndex = 0
    /*手指按下的字母改变接口*/
    private var listener: onWordsChangeListener? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    /**
     * 初始化画笔
     */
    private fun init() {
        wordsPaint = Paint()
        wordsPaint!!.color = Color.parseColor("#F7F7F7")
        wordsPaint!!.isAntiAlias = true
        wordsPaint!!.textSize = 40f
        wordsPaint!!.typeface = Typeface.DEFAULT_BOLD

        bgPaint = Paint()
        bgPaint!!.isAntiAlias = true
        bgPaint!!.color = Color.parseColor("#1dcdef")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        itemWidth = measuredWidth
        //使得边距好看一些
        val height = measuredHeight - 10
        itemHeight = height / 27
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in words.indices) {
            //判断是不是我们按下的当前字母
            if (touchIndex == i) {
                //绘制文字圆形背景
                canvas.drawCircle((itemWidth / 2).toFloat(), (itemHeight / 2 + i * itemHeight).toFloat(), 23f, bgPaint!!)
                wordsPaint!!.color = Color.WHITE
            } else {
                wordsPaint!!.color = Color.GRAY
            }
            //获取文字的宽高
            val rect = Rect()
            wordsPaint!!.getTextBounds(words[i], 0, 1, rect)
            val wordWidth = rect.width()
            //绘制字母
            val wordX = (itemWidth / 2 - wordWidth / 2).toFloat()
            val wordY = (itemWidth / 2 + i * itemHeight).toFloat()
            canvas.drawText(words[i], wordX-2, wordY+6, wordsPaint!!)
        }
    }

    /**
     * 当手指触摸按下的时候改变字母背景颜色
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val y = event.y
                //获得我们按下的是那个索引(字母)
                val index = (y / itemHeight).toInt()
                if (index != touchIndex)
                    touchIndex = index
                //防止数组越界
                if (listener != null && 0 <= touchIndex && touchIndex <= words.size - 1) {
                    //回调按下的字母
                    listener!!.wordsChange(words[touchIndex])
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
            }
        }//手指抬起,不做任何操作
        return true
    }

    /*设置当前按下的是那个字母*/
    fun setTouchIndex(word: String) {
        for (i in words.indices) {
            if (words[i] == word) {
                touchIndex = i
                invalidate()
                return
            }
        }
    }

    /*手指按下了哪个字母的回调接口*/
    interface onWordsChangeListener {
        fun wordsChange(words: String)
    }

    /*设置手指按下字母改变监听*/
    fun setOnWordsChangeListener(listener: onWordsChangeListener) {
        this.listener = listener
    }
}

