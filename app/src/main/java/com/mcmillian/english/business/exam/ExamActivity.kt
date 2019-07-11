package com.mcmillian.english.business.exam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mcmillian.english.R
import com.mcmillian.english.base.BaseActivity
import kotlinx.android.synthetic.main.activity_exam.*
import kotlinx.coroutines.launch

class ExamActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)
        launch { onCreate() }
    }

    suspend fun onCreate(){
        stateLayout.showProgressView("正在加载试题....")

    }
}
