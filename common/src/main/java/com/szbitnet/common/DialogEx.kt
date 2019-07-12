package com.szbitnet.common

import android.content.Context
import android.content.DialogInterface
import androidx.fragment.app.Fragment
import kotlinx.coroutines.suspendCancellableCoroutine
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.selector
import kotlin.coroutines.resume

suspend fun AnkoContext<*>.selector(
    title: CharSequence? = null,
    items: List<CharSequence>
) = ctx.selector(title, items)

suspend fun Fragment.selector(title: CharSequence? = null, items: List<CharSequence>) = requireContext().selector(title, items)

suspend fun Context.selector(title: CharSequence? = null, items: List<CharSequence>): Int {

    return suspendCancellableCoroutine { continuation ->
        this.selector(title, items) { d, i ->
            continuation.resume(i)

            continuation.invokeOnCancellation {
                d.cancel()
            }
        }
    }
}