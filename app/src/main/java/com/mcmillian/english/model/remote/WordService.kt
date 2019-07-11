package com.mcmillian.english.model.remote

import com.mcmillian.english.model.data.Book
import com.mcmillian.english.model.data.Unit
import com.mcmillian.english.model.data.Word
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WordService {

    /**
     * 获取所有课本
     */
    @GET("book")
    suspend fun getBooks(): List<Book>


    /**
     * 获取所有课本
     */
    @GET("book/{bookId}/unit")
    suspend fun getUnitByBook(@Path("bookId") bookId: Int): List<Unit>

    /**
     * 根据某课本所有单词
     * @param count 个数，为 0 获取所有
     */
    @GET("book/{bookId}/word")
    suspend fun getWordByBook(@Path("bookId") bookId: Int, count: Int = 50): List<Word>

    /**
     * 根据单元获取单词
     */
    @GET("unit/{unitId}/word")
    suspend fun getWordByUnit(@Path("unitId") unitId: Int): List<Word>

    /**
     * 根据单元范围获取单词
     */
    @GET("unit/word")
    suspend fun getWordByUnit(@Query("units") units: String): List<Word>

    suspend fun getWordByUnit(vararg units: Int) = getWordByUnit(units.joinToString())
}