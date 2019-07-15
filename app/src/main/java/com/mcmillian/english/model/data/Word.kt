package com.mcmillian.english.model.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index("unitId")],foreignKeys = [ForeignKey(
    entity = Unit::class,
    parentColumns = ["id"],
    childColumns = ["unitId"],
    onUpdate = ForeignKey.CASCADE,
    onDelete = ForeignKey.SET_DEFAULT
)])
data class Word(
    /**
     * 单元
     */
    val unitId: Int,
    /**
     * 英文
     */
    val english: String,
    /**
     * 中文
     */
    val chinese: String,
    /**
     * 词性
     */
    val type: String?=null,
    /**
     * 提示
     */
    val hint: String?=null
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}