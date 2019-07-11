package com.mcmillian.english.model.remote

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.create


object RetrofitManager {
    var retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var wordService = retrofit.create<WordService>()
}