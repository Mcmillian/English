package com.szbitnet.common

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.support.v4.onPageChangeListener

/**
 * View类扩展
 * Created by Administrator on 2018/1/12 0012.
 */

/**
 * 将BottomNavigationView和ViewPager、Fragment进行联动
 * @param viewPager 要绑定的[ViewPager]
 * @param fragments viewPager要显示的[Fragment]
 * @param offscreenPageLimit viewpager预加载的页数
 */
fun BottomNavigationView.setupWithViewPager(viewPager: ViewPager, fragments: List<Fragment>, offscreenPageLimit: Int = fragments.size) {

    this.setOnNavigationItemSelectedListener {
        viewPager.currentItem = it.order
        true
    }
    viewPager.offscreenPageLimit = offscreenPageLimit
    viewPager.adapter = object : FragmentPagerAdapter((this.context as FragmentActivity).supportFragmentManager) {
        override fun getItem(position: Int) = fragments[position]
        override fun getCount() = fragments.size
    }
    viewPager.onPageChangeListener {
        onPageSelected {
            this@setupWithViewPager.menu.getItem(it).isChecked = true
        }
    }
}

fun View.snackbar(text:String,duration:Int = Snackbar.LENGTH_SHORT){
    Snackbar.make(this,text,duration).show()
}

/**
 * Button的防抖点击
 * @param ms 防抖间隔，单位ms，默认为500
 */
fun Button.throttleClick(ms:Long = 500, click:()->Unit){
    val lastClickTime = this.getTag(10086) as? Long ?:0
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - lastClickTime >= ms) {
        this.setTag(10086,currentClickTime)
        click()
    }else{
        Log.v("防抖","你点的也太快了吧")
    }
}