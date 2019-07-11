package com.mcmillian.english.base

import androidx.fragment.app.Fragment
import com.szbitnet.common.AnkoLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive

abstract class BaseFragment: Fragment(), CoroutineScope by MainScope(), AnkoLogger {

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
    override fun onDetach() {
        super.onDetach()
        cancel()
    }
}