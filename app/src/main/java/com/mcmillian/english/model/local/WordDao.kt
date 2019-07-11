package com.mcmillian.english.model.local

import androidx.room.*
import com.mcmillian.english.model.data.Book
import com.mcmillian.english.model.data.Unit
import com.mcmillian.english.model.data.Word

@Dao
interface WordDao {

    /**
     * 根据单元获取单词
     */
    @Query("SELECT * FROM word  WHERE  unitId = :unitId")
    suspend fun getWordByUnit(unitId: Int): List<Word>

    /**
     * 根据单元范围获取单词
     */
    @Query("SELECT * FROM word WHERE unitId IN (:units)")
    suspend fun getWordByUnit(vararg units: Int): List<Word>

    /**
     * 获取课本所有单元的单词
     */
    @Query("SELECT w.* FROM word w,unit u WHERE u.bookId = :bookId AND w.unitId = u.id")
    suspend fun getWordByBook(bookId: Int): List<Word>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg word: Word)

    @Delete
    suspend fun delete(word: Word)


    /**
     * 获取所有的课本
     */
    @Query("SELECT * FROM book")
    suspend fun getBooks(): List<Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg book: Book)

    /**
     * 根据课本获取单元
     */
    @Query("SELECT * FROM unit WHERE bookId = :bookId")
    suspend fun getUnits(bookId:Int): List<Unit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(unit: Unit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg unit: Unit)
}