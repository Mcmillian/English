package com.mcmillian.english.model.data

data class Response<T>(
    val code:Int,
    val message:String,
    val data:T
){
    val isOk get()= code == 0;
}