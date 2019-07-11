package com.szbitnet.common

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class FullPreference<T>(context: Context, fileName: String, val name: String, val default: T) :
    ReadWriteProperty<Any?, T> {

    private val prefs: SharedPreferences

    init {
        try {
            prefs = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
            Log.d("prefs",prefs.toString())
        }catch (e:Exception){
            e.printStackTrace()
            throw e
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(name, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }

    /**
     * 查找数据 返回给调用方法一个具体的对象
     * 如果查找不到类型就采用反序列化方法来返回类型
     * default是默认对象 以防止会返回空对象的异常
     * 即如果name没有查找到value 就返回默认的序列化对象，然后经过反序列化返回
     */
    private fun <T> findPreference(name: String, default: T): T = with(prefs) {
        return when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            is MutableSet<*> -> getStringSet(name, default as MutableSet<String>)
            else -> deSerialization(getString(name, serialize(default)))
        } as T
    }

    private fun <T> putPreference(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)

            is MutableSet<*> -> putStringSet(name, default as MutableSet<String>)
            else -> putString(name, serialize(value))
        }.apply()
    }

    /**
     * 删除全部数据
     */
    fun clearPreference() {
        prefs.edit().clear().apply()
    }

    /**
     * 根据key删除存储数据
     */
    fun clearPreference(key: String) {
        prefs.edit().remove(key).apply()
    }


    private fun <A> serialize(obj: A): String {
        return Gson().toJson(obj)
    }

    private inline fun <reified A> deSerialization(str: String): A {
        return Gson().fromJson(str)
    }

    private inline fun <reified A> Gson.fromJson(str: String): A {
        return this.fromJson(str, object : TypeToken<A>() {}.type)
    }

}