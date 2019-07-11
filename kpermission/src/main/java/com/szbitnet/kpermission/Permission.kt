package com.szbitnet.kpermission

import android.Manifest
import android.app.Activity
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


suspend fun AppCompatActivity.requestPermission(vararg permissions: String): BooleanArray {
    return suspendCoroutine { continuation ->
        val fragment = PermissionsFragment.getInstance(this@requestPermission)
        fragment.setPermissionResultCallback { continuation.resume(it) }
        fragment.requestPermissions(arrayOf(*permissions))
    }
}

suspend fun AppCompatActivity.requestPermission(permissions: List<String>): BooleanArray =
    suspendCoroutine { continuation ->
        val fragment = PermissionsFragment.getInstance(this@requestPermission)
        fragment.setPermissionResultCallback { continuation.resume(it) }
        fragment.requestPermissions(permissions.toTypedArray())
    }


suspend fun AppCompatActivity.requestPermission(permission: String) = requestPermission(*arrayOf(permission))[0]

fun AppCompatActivity.isPermissionFailForever(permission: String) =
    !ActivityCompat.shouldShowRequestPermissionRationale(this, permission)