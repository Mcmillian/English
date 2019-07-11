package com.szbitnet.common

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import kotlinx.coroutines.delay
import org.jetbrains.anko.sdk27.coroutines.textChangedListener

class TextWatcherBuilder {
    internal var beforeTextChanged: ((s: CharSequence, start: Int, count: Int, after: Int) -> Unit)? = null
    internal var onTextChanged: ((s: CharSequence, start: Int, before: Int, count: Int) -> Unit)? = null
    internal var afterTextChanged: ((s: Editable) -> Unit)? = null

    fun afterTextChanged(action: (s: Editable) -> Unit) {
        afterTextChanged = action
    }

    fun beforeTextChanged(action: (s: CharSequence, start: Int, count: Int, after: Int) -> Unit) {
        beforeTextChanged = action
    }

    fun onTextChanged(action: (s: CharSequence, start: Int, before: Int, count: Int) -> Unit) {
        onTextChanged = action
    }
}

fun EditText.addTextWatcher(init: TextWatcherBuilder.() -> Unit) {
    val builder = TextWatcherBuilder().apply(init)
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            builder.afterTextChanged?.invoke(s)
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            builder.beforeTextChanged?.invoke(s, start, count, after)
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            builder.onTextChanged?.invoke(s, start, before, count)
        }
    })
}

/**
 * 输入完成后过一些时间才会触发
 * @param afterTime 触发时间
 */
fun EditText.afterTextChanged(afterTime:Long = 500,block:(s: Editable)->Unit){
    val controlledRunner = ControlledRunner<Editable>()
    this.textChangedListener {
        afterTextChanged {
            val s = controlledRunner.cancelPreviousThenRun {
                delay(afterTime)
                it!!
            }
            block(s)
        }
    }
}