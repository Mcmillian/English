package com.mcmillian.english.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    @PrimaryKey
    val id:Int,
    /**
     * 名字，如：八年级（上）
     */
    val name:String
)