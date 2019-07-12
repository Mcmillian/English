package com.mcmillian.english.model.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.mcmillian.english.BuildConfig
import com.mcmillian.english.model.data.Book
import com.mcmillian.english.model.data.Response
import com.mcmillian.english.model.data.Unit
import com.mcmillian.english.model.data.Word
import com.mcmillian.english.model.local.WordDao
import com.mcmillian.english.model.remote.WordService
import java.lang.RuntimeException

class WordRepository(private val service: WordService, private val dao: WordDao) {

    /**
     * 获取所有的课本
     */
    @WorkerThread
    suspend fun getBooks(forceNew:Boolean = false): Response<List<Book>> {
        suspend fun getByNetwork(): Response<List<Book>> {
            val response = service.getBooks()
            try {
                if (response.isOk){
                    dao.insert(*response.data.toTypedArray())
                }
            }catch (e:Exception){
                if (BuildConfig.DEBUG){
                    Log.e("数据库","存储数据失败${e.message}")
                    e.printStackTrace()
                }
            }
            return response
        }

        if (forceNew){
            return getByNetwork();
        }
        val books = dao.getBooks()
        if (books.isNotEmpty()){
            return Response.Success(books)
        }
        return getByNetwork();
    }

    /**
     * 根据课本获取单元
     */
    @WorkerThread
    suspend fun getUnitByBook(bookId: Int,forceNew:Boolean = false): Response<List<Unit>> {
        suspend fun getByNetwork(): Response<List<Unit>> {
            val response = service.getUnitByBook(bookId)
            try {
                if (response.isOk){
                    dao.insert(*response.data.toTypedArray())
                }
            }catch (e:Exception){
                if (BuildConfig.DEBUG){
                    Log.e("数据库","存储数据失败${e.message}")
                    e.printStackTrace()
                }
            }
            return response
        }

        if (forceNew){
            return getByNetwork();
        }
        val units = dao.getUnits(bookId)
        if (units.isNotEmpty()){
            return Response.Success(units)
        }
        return getByNetwork();
    }

    @WorkerThread
    suspend fun getWordByUnit(unitId:Int,forceNew:Boolean = false): Response<List<Word>> {
        suspend fun getByNetwork(): Response<List<Word>> {
            val response = service.getWordByUnit(unitId)
            try {
                if (response.isOk){
                    dao.insert(*response.data.toTypedArray())
                }
            }catch (e:Exception){
                if (BuildConfig.DEBUG){
                    Log.e("数据库","存储数据失败${e.message}")
                    e.printStackTrace()
                }
            }
            return response
        }

        if (forceNew){
            return getByNetwork()
        }
        val words = dao.getWordByUnit(unitId)
        if(words.isNotEmpty()){
            return Response.Success(words)
        }
        return getByNetwork()
    }

    @WorkerThread
    suspend fun insert(word: Word) {
        dao.insert(word)
    }
}