package com.mcmillian.english.base

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.mcmillian.english.R
import com.szbitnet.common.AnkoLogger
import com.szbitnet.common.bottomAlert
import com.szbitnet.kpermission.isPermissionFailForever
import com.szbitnet.kpermission.requestPermission
import kotlinx.coroutines.*
import android.content.Intent
import android.net.Uri


abstract class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statusBarLightMode()
    }

    fun setDisplayHomeAsUpEnabled(id: Int = R.id.toolbar) {
        findViewById<Toolbar>(id)?.let {
            setSupportActionBar(it)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    internal fun withPermission(permissions: String, success: () -> Unit) {
        GlobalScope.launch(Dispatchers.Main){
            val b = requestPermission(permissions)
            if (b){
                success()
                return@launch
            }
            if (isPermissionFailForever(permissions)) {
                bottomAlert {
                    title = "运行权限"
                    message = "有权限被永久拒绝，本功能无法使用，是否去设置？"
                    firstButton("设置"){
                        val localIntent = Intent()
                        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS";
                        localIntent.data = Uri.fromParts("package", packageName, null);
                        startActivity(localIntent)
                    }
                    secondButton("不使用"){
                        finish()
                    }
                }.show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        cancel()
    }

    /**
     * 左上角如果显示了返回键后，点击会触发
     */
    open fun onHomeClick() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onHomeClick()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    fun statusBarLightMode() {
        fun FlymeSetStatusBarLightMode(window: Window?, dark: Boolean): Boolean {
            var result = false
            if (window != null) {
                try {
                    val lp = window.getAttributes()
                    val darkFlag = WindowManager.LayoutParams::class.java
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                    val meizuFlags = WindowManager.LayoutParams::class.java
                        .getDeclaredField("meizuFlags")
                    darkFlag.isAccessible = true
                    meizuFlags.isAccessible = true
                    val bit = darkFlag.getInt(null)
                    var value = meizuFlags.getInt(lp)
                    if (dark) {
                        value = value or bit
                    } else {
                        value = value and bit.inv()
                    }
                    meizuFlags.setInt(lp, value)
                    window.setAttributes(lp)
                    result = true
                } catch (e: Exception) {

                }

            }
            return result
        }

        /**
         * 需要MIUIV6以上
         *
         * @param activity
         * @param dark     是否把状态栏文字及图标颜色设置为深色
         * @return boolean 成功执行返回true
         */
        fun MIUISetStatusBarLightMode(activity: Activity, dark: Boolean): Boolean {
            var result = false
            val window = activity.window
            if (window != null) {
                val clazz = window::class.java
                try {
                    var darkModeFlag = 0
                    val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                    val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                    darkModeFlag = field.getInt(layoutParams)
                    val extraFlagField = clazz.getMethod(
                        "setExtraFlags",
                        Int::class.javaPrimitiveType,
                        Int::class.javaPrimitiveType
                    )
                    if (dark) {
                        extraFlagField.invoke(window, darkModeFlag, darkModeFlag)//状态栏透明且黑色字体
                    } else {
                        extraFlagField.invoke(window, 0, darkModeFlag)//清除黑色字体
                    }
                    result = true

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                        if (dark) {
                            activity.window.decorView.systemUiVisibility =
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        } else {
                            activity.window.decorView.systemUiVisibility =
                                View.SYSTEM_UI_FLAG_VISIBLE
                        }
                    }
                } catch (e: Exception) {

                }

            }
            return result
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            MIUISetStatusBarLightMode(this, true)
            FlymeSetStatusBarLightMode(window, true)
        }
    }

}