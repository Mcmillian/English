package com.mcmillian.english.business.exam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import com.mcmillian.english.R
import com.mcmillian.english.model.local.AppDatabase
import kotlinx.android.synthetic.main.dialog_word_range_select.*
import kotlinx.coroutines.*
import org.jetbrains.anko.support.v4.toast
import kotlin.random.Random


class WordRangeSelectDialog(private val fm: FragmentManager, val gradeRange: IntRange) :
    DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_word_range_select, container, true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var grade = 0

        val unitSectionAdapter = WordRangeSectionAdapter(arrayListOf()) { position, title ->
            GlobalScope.launch(Dispatchers.Main) {
                val words = withContext(Dispatchers.IO) {
                    AppDatabase.INSTANCE.wordDao().getWord(grade, position)
                }
                toast(words.size)
            }
        }
        gradeRecyclerView.adapter =
            WordRangeSectionAdapter(gradeRange.map { "${it}年级" }.toMutableList()) { position, title ->
                gradeRecyclerView.visibility = View.GONE
                selectedGradeTextView.visibility = View.VISIBLE
                selectedGradeTextView.text = title

                grade = position

                unselectedUnitTextView.visibility = View.VISIBLE
                unitRecyclerView.visibility = View.GONE
                unselectedUnitTextView.text = "正在获取单元"

                GlobalScope.launch(Dispatchers.Main) {
                    delay(500)
                    val unitCount = Random(System.currentTimeMillis()).nextInt(1, 14)
                    unitSectionAdapter.sections = (1..unitCount).map { "${it}单元" }.toMutableList()
                    unitSectionAdapter.notifyDataSetChanged()

                    unselectedUnitTextView.visibility = View.GONE
                    unitRecyclerView.visibility = View.VISIBLE
                }
            }
        gradeRecyclerView.layoutManager = GridLayoutManager(context, 4)
        unitRecyclerView.adapter = unitSectionAdapter
        unitRecyclerView.layoutManager = GridLayoutManager(context, 4)
    }

    fun show() {
        show(fm, "WordRangeSelectDialog")
    }
}

fun AppCompatActivity.wordRangeSelectDialog(gradeRange: IntRange): WordRangeSelectDialog {
    return WordRangeSelectDialog(this.supportFragmentManager, gradeRange)
}

fun Fragment.wordRangeSelectDialog(gradeRange: IntRange): WordRangeSelectDialog {
    return WordRangeSelectDialog(this.childFragmentManager, gradeRange)
}