package com.mcmillian.english.model.data

sealed class Response<T>(
    val code: Int,
    val message: String,
    val data: T
) {
    val isOk get() = code == 0;

    class Success<T>(data: T) : Response<T>(0, "获取数据成功", data)

    class Failure<T>(code: Int, message: String) : Response<T?>(code, message,null)

    override fun toString(): String {
        return if (isOk) {
            "[OK] $data"
        } else {
            "[Error] $message"
        }
    }
}