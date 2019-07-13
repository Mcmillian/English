package com.mcmillian.english.business.exam

import com.mcmillian.english.model.data.Book
import com.mcmillian.english.model.data.Unit
import com.mcmillian.english.model.data.Word

interface IWordView {
    fun onPullBookStart()
    fun onPullBookSuccess(books: List<Book>)
    fun onPullBookFail(error: String)
    fun onPullBookEnd(){}

    fun onPullUnitStart(bookId:Int)
    fun onPullUnitSuccess(bookId:Int,units:List<Unit>)
    fun onPullUnitFail(bookId:Int,error: String)
    fun onPullUnitEnd(bookId:Int){}

    fun onPullWordStart(unitId: Int)
    fun onPullWordSuccess(unitId: Int, words: List<Word>)
    fun onPullWordFail(unitId: Int, error: String)
    fun onPullWordEnd(unitId: Int){}
}