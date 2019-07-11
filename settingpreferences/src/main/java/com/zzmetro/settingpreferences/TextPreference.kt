package com.zzmetro.settingpreferences

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import org.jetbrains.anko.*
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.content.ContextCompat
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import org.jetbrains.anko.appcompat.v7.tintedImageView

class TextPreference @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val splitColor = Color.parseColor("#33E0E0E0")

    private lateinit var contentView: TextView
    var content: CharSequence?
        get() = contentView.text
        set(value) {
            if (value == null) {
                contentView.visibility = View.GONE
            } else {
                contentView.visibility = View.VISIBLE
            }
            contentView.text = value
        }
    private lateinit var iconView: ImageView
    /**
     * 设置icon
     */
    var iconRes: Int = 0
        set(value) {
            if (value == 0) {
                iconView.visibility = View.GONE
            } else {
                iconView.setImageResource(value)
                iconView.visibility = View.VISIBLE
            }
        }
        @Deprecated("没有获取方法", level = DeprecationLevel.HIDDEN)
        get
    /**
     * 设置icon
     */
    var icon: Drawable?
        set(value) {
            if (value == null) {
                iconView.visibility = View.GONE
            } else {
                iconView.setImageDrawable(value)
                iconView.visibility = View.VISIBLE
            }
        }
        get() = iconView.image

    @ColorRes
    var iconTintRes: Int? = -1
        set(value) {
            if (value != null && value != -1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    iconView.imageTintList =
                        ColorStateList.valueOf(ContextCompat.getColor(context, value))
                } else {
                    iconView.setColorFilter(
                        ContextCompat.getColor(context, value),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    );
                }
            }
        }
        @Deprecated("没有获取方法", level = DeprecationLevel.HIDDEN)
        get

    @ColorInt
    var iconTint: Int? = -1
        set(value) {
            if (value != null && value != -1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    iconView.imageTintList = ColorStateList.valueOf(value)
                } else {
                    iconView.setColorFilter(value, android.graphics.PorterDuff.Mode.SRC_IN);
                }
            }
        }
        @Deprecated("没有获取方法", level = DeprecationLevel.HIDDEN)
        get

    lateinit var navIconView: ImageView
        private set

    /**
     * 设置icon
     */
    var navIconRes: Int = 0
        set(value) {
            if (value == 0) {
                navIconView.visibility = View.GONE
            } else {
                navIconView.setImageResource(value)
                navIconView.visibility = View.VISIBLE
            }
        }
        @Deprecated("没有获取方法", level = DeprecationLevel.HIDDEN)
        get


    /**
     * 设置icon
     */
    var navIcon: Drawable?
        set(value) {
            if (value == null) {
                navIconView.visibility = View.GONE
            } else {
                navIconView.setImageDrawable(value)
                navIconView.visibility = View.VISIBLE
            }
        }
        get() = navIconView.image

    private lateinit var summaryTextView: TextView
    var summary: CharSequence?
        get() = summaryTextView.text
        set(value) {
            if (value.isNullOrBlank()) {
                summaryTextView.visibility = View.GONE
            } else {
                summaryTextView.visibility = View.VISIBLE
            }
            summaryTextView.text = value
        }


    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextPreference)
        val title = typedArray.getString(R.styleable.TextPreference_title) ?: ""
        val summary = typedArray.getString(R.styleable.TextPreference_summary)
        val summaryHint = typedArray.getString(R.styleable.TextPreference_summaryHint)
        val content = typedArray.getString(R.styleable.TextPreference_text) ?: ""
        val icon: Drawable? = typedArray.getDrawable(R.styleable.TextPreference_icon)
        val iconTint: Int = typedArray.getColor(R.styleable.TextPreference_iconTint, -1)
        val navIcon: Drawable? = typedArray.getDrawable(R.styleable.TextPreference_navIcon)
        typedArray.recycle();

        addView(context.linearLayout {
            backgroundResource = R.drawable.bg_ripple
            lparams(matchParent, wrapContent)
            orientation = LinearLayout.VERTICAL
            setOnClickListener {
                onClick?.invoke(it)
            }

            view {
                backgroundColor = splitColor
            }.lparams(matchParent, 1){
                marginStart = dip(16)
            }

            linearLayout {
                lparams(matchParent, wrapContent)
                padding = dip(16)
                gravity = android.view.Gravity.CENTER_VERTICAL
                orientation = LinearLayout.HORIZONTAL

                iconView = tintedImageView(icon) {
                    if (iconTint != -1) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            this.imageTintList = ColorStateList.valueOf(iconTint)
                        } else {
                            setColorFilter(iconTint, android.graphics.PorterDuff.Mode.SRC_IN);
                        }
                    }
                }.lparams(dip(22), dip(22)) {
                    rightMargin = dip(16)
                }
                iconView.visibility = if (icon != null) View.VISIBLE else View.GONE

                linearLayout {
                    orientation = LinearLayout.VERTICAL
                    textView {
                        textColor = resources.getColor(android.R.color.black)
                        textSize = 16f
                        text = title
                    }
                    summaryTextView = textView {
                        text = summary
                        hint = summaryHint
                        maxLines = 2
                    }.lparams {
                        topMargin = dip(8)
                    }
                    summaryTextView.visibility =
                        if (summary != null || summaryHint != null) View.VISIBLE else View.GONE
                }.lparams(0, wrapContent, 1f) {
                    rightMargin = dip(16)
                }


                contentView = textView {
                    text = content
                    textSize = 16f
                }

                navIconView = imageView(navIcon){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        this.imageTintList = ColorStateList.valueOf(Color.GRAY)
                    } else {
                        setColorFilter(Color.GRAY, android.graphics.PorterDuff.Mode.SRC_IN);
                    }
                }.lparams(dip(22), dip(22)) {
                    leftMargin = dip(16)
                }
                navIconView.visibility = if (navIcon != null) View.VISIBLE else View.GONE
            }

            view {
                backgroundColor = splitColor
            }.lparams(matchParent, 1){
                marginStart = dip(16)
            }
        })


    }


    private var onClick: ((View) -> Unit)? = null

    fun onClick(action: (View) -> Unit) {
        onClick = action
    }


}