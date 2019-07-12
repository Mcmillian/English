package com.mcmillian.english.business.exam

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mcmillian.english.R
import com.mcmillian.english.base.BaseActivity
import com.mcmillian.english.model.data.Book
import com.mcmillian.english.model.data.Unit
import com.mcmillian.english.model.data.Word
import com.szbitnet.common.selector
import kotlinx.android.synthetic.main.activity_exam.*
import kotlinx.coroutines.launch
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

fun Context.navToExam(type:Int){
    startActivity<ExamActivity>("type" to type)
}

class ExamActivity : BaseActivity(),IWordView {
    private val type by lazy { intent.getIntExtra("type",0) }

    private val bookPresenter by lazy{
        BookPresenter(this,this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)
        bookPresenter.getBook()
    }

    override fun onPullBookStart() {
        stateLayout.showProgressView("正在加载课本....")
    }

    override fun onPullBookSuccess(books: List<Book>) {
        launch {
            val position = selector("选择课本", books.map { it.name })
            bookPresenter.getUnit(books[position].id)
        }
    }

    override fun onPullBookFail(error: String) {
        stateLayout.showErrorView(error)
        stateLayout.setErrorAction {
            bookPresenter.getBook()
        }
    }

    override fun onPullUnitStart(bookId:Int) {
        stateLayout.showProgressView("正在加载单元....")
    }

    override fun onPullUnitSuccess(bookId:Int,units: List<Unit>) {
        launch {
            val position = selector("选择单元", units.map { it.name })
            bookPresenter.getWordByUnit(units[position].id)
        }
    }

    override fun onPullUnitFail(bookId:Int,error: String) {
        stateLayout.showErrorView(error)
        stateLayout.setErrorAction {
            bookPresenter.getUnit(bookId)
        }
    }

    override fun onPullWordStart(unitId: Int) {
        stateLayout.showProgressView("正在加载单词....")
    }

    override fun onPullWordSuccess(unitId: Int, words: List<Word>) {
        toast("word"+words.size)
    }

    override fun onPullWordFail(unitId: Int, error: String) {
        stateLayout.showErrorView(error)
        stateLayout.setErrorAction {
            bookPresenter.getWordByUnit(unitId)
        }
    }
}
