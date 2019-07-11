package com.mcmillian.english.business.main

import android.os.Bundle
import androidx.annotation.IntDef
import com.mcmillian.english.R
import com.mcmillian.english.base.BaseActivity
import com.mcmillian.english.business.exam.wordRangeSelectDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.selector

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        examButton.onClick {
            selector("题目方式", listOf("听写", "翻译", "听写 + 翻译")) { _, position ->
                
            }
        }
    }
}
