package com.mcmillian.english.base

import com.mcmillian.english.BuildConfig
import com.mcmillian.english.model.data.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import java.net.ConnectException
import java.net.SocketTimeoutException

open class BasePresenter(private val scope: CoroutineScope) : CoroutineScope by scope, AnkoLogger {

    fun <T> doWebServer(
        block: CoroutineScope.() -> Response<T>,
        onSuccess: (T) -> Unit,
        onFail: (String) -> Unit,
        onEnd: (() -> Unit)? = null
    ) {
        scope.launch {
            try {
                val response =  block()
                if (response.isOk){
                    onSuccess(response.data)
                }else{
                    onFail(response.message)
                }
            } catch (e: Exception) {
                onResponseException(e, onFail)
                return@launch
            }finally {
                onEnd?.invoke()
            }
        }
    }

    private fun onResponseException(e: Exception, onFail: (String) -> Unit) {
        if (BuildConfig.DEBUG) e.printStackTrace()
        if (e is RuntimeException) {
            onFail(e.message ?: "连接系统出错,稍后再试")
        } else if (e is SocketTimeoutException ) {
            onFail("连接系统超时,请稍后再试")
        } else if (e is ConnectException ) {
            onFail("无法连接服务器,请检查网络后再试")
        } else{
            onFail("连接系统出错,稍后再试")
        }
    }
}