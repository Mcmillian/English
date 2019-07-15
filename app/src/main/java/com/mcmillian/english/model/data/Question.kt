package com.mcmillian.english.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
sealed class Question {
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0

    var title: String = ""
        protected set

    var answer: String = ""
        protected set

    //是否要读题目
    var read = false
        protected set

    /**
     * 每个字母给一秒钟的时间写
     */
    val time get() = answer.length * 1000

    @Entity
    class Listen(title: String, answer: String) : Question() {
        init {
            this.title = title
            this.answer = answer
            read = true
        }
    }

    @Entity
    class Write(title: String, answer: String) : Question() {
        init {
            this.title = title
            this.answer = answer
            read = true
        }
    }
}