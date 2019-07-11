package com.mcmillian.english.model.repository

import androidx.annotation.WorkerThread
import com.mcmillian.english.model.data.Book
import com.mcmillian.english.model.data.Unit
import com.mcmillian.english.model.data.Word
import com.mcmillian.english.model.local.WordDao
import com.mcmillian.english.model.remote.WordService

class WordRepository(private val service: WordService, private val dao: WordDao) {

    /**
     * 获取所有的课本
     */
    @WorkerThread
    suspend fun getBooks(forceNew:Boolean = false): List<Book> {
        if (forceNew){
            val books = service.getBooks()
            dao.insert(*books.toTypedArray())
            return books
        }
        var books = dao.getBooks()
        if (books.isNotEmpty()){
            return books
        }
        books = service.getBooks()
        dao.insert(*books.toTypedArray())
        return books
    }

    /**
     * 根据课本获取单元
     */
    @WorkerThread
    suspend fun getUnitByBook(bookId: Int,forceNew:Boolean = false): List<Unit>{
        if (forceNew){
            val units = service.getUnitByBook(bookId)
            dao.insert(*units.toTypedArray())
            return units
        }
        var units = dao.getUnits(bookId)
        if (units.isNotEmpty()){
            return units
        }
        units = service.getUnitByBook(bookId)
        dao.insert(*units.toTypedArray())
        return units
    }

    @WorkerThread
    suspend fun getWordByUnit(unitId:Int,forceNew:Boolean = false): List<Word> {
        if (forceNew){
            val words = service.getWordByUnit(unitId)
            dao.insert(*words.toTypedArray())
            return words
        }
        var words = dao.getWordByUnit(unitId)
        if(words.isNotEmpty()){
            return words
        }
        words = service.getWordByUnit(unitId);
        dao.insert(*words.toTypedArray())
        return words
    }

    @WorkerThread
    suspend fun insert(word: Word) {
        dao.insert(word)
    }
}