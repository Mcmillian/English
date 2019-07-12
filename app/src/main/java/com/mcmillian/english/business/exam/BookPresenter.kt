package com.mcmillian.english.business.exam

import com.mcmillian.english.base.BasePresenter
import com.mcmillian.english.model.local.AppDatabase
import com.mcmillian.english.model.remote.WebService
import com.mcmillian.english.model.repository.WordRepository
import kotlinx.coroutines.CoroutineScope

class BookPresenter(scope: CoroutineScope,private val view:IWordView) : BasePresenter(scope) {

    private val wordRepository by lazy {
        WordRepository(WebService.wordService,AppDatabase.INSTANCE.wordDao())
    }

    fun getBook(){
        view.onPullBookStart();
        doWebServer(
            {
                wordRepository.getBooks()
            },
            view::onPullBookSuccess,
            view::onPullBookFail,
            view::onPullBookEnd
        )
    }

    fun getUnit(bookId:Int){
        view.onPullUnitStart(bookId);
        doWebServer(
            {
                wordRepository.getUnitByBook(bookId)
            },
            {
                view.onPullUnitSuccess(bookId,it)
            },
            {
                view.onPullUnitFail(bookId,it)
            },
            {
                view.onPullUnitEnd(bookId)
            }
        )
    }

    fun getWordByUnit(unitId:Int){
        view.onPullWordStart(unitId);
        doWebServer(
            {
                wordRepository.getWordByUnit(unitId)
            },
            {
                view.onPullWordSuccess(unitId,it)
            },
            {
                view.onPullWordFail(unitId,it)
            },
            {
                view.onPullWordEnd(unitId)
            }
        )
    }
}