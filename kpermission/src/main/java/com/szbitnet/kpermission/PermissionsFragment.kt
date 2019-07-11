package com.szbitnet.kpermission

import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

internal class PermissionsFragment : Fragment() {
    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 404
        private const val TAG = "PermissionsFragment"
        fun getInstance(activity: AppCompatActivity): PermissionsFragment {
            val reference = WeakReference(activity)
            return (reference.get()?.supportFragmentManager?.findFragmentByTag(TAG) as? PermissionsFragment)
                    ?: PermissionsFragment().also {
                        reference.get()?.apply {
                            supportFragmentManager
                                    .beginTransaction()
                                    .add(it, TAG)
                                    .commitAllowingStateLoss()
                        }
                        reference.get()?.supportFragmentManager?.executePendingTransactions()
                    }
        }
    }

    private var permissionsResultCallback: ((BooleanArray) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.retainInstance = true
    }

    @TargetApi(Build.VERSION_CODES.M)
    internal fun requestPermissions(permissions: Array<String>) = GlobalScope.launch(Dispatchers.Main) {
        requestPermissions(permissions, PERMISSIONS_REQUEST_CODE)
    }


    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != PERMISSIONS_REQUEST_CODE) return
        val permissionResults = BooleanArray(permissions.size) {
            grantResults.getOrNull(it) == PackageManager.PERMISSION_GRANTED
        }
        permissionsResultCallback?.invoke(permissionResults)
    }

    fun setPermissionResultCallback(callback: (BooleanArray) -> Unit) {
        this.permissionsResultCallback = callback
    }
}