package com.mcmillian.english.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
sealed class Question {
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0

    var title:String=""

    var answer:String = ""


    @Entity
    class Listen:Question(){

    }
}