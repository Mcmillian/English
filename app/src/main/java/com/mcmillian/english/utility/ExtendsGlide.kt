package com.mcmillian.english.utility

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView


fun ImageView.load(
    url: String?,
    context: Context? = null,
    init: (GlideRequest<Drawable>.() -> Unit)? = null
) {
    if (url == null) {
        Log.v("Glide", "加载的图片路径为空")
        return
    }
    if (context != null) {
        if (init != null) {
            GlideApp.with(context).load(url).apply(init).into(this)
        } else {
            GlideApp.with(context).load(url).into(this)
        }
    } else {
        if (init != null) {
            GlideApp.with(this).load(url).apply(init).into(this)
        } else {
            GlideApp.with(this).load(url).into(this)
        }
    }

}
