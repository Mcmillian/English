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
import android.text.InputFilter
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import org.jetbrains.anko.appcompat.v7.tintedImageView

class EditPreference @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val splitColor = Color.parseColor("#33E0E0E0")

    lateinit var contentView: EditText
        private set
    var content: CharSequence?
        get() = contentView.text
        set(value) {
            if (value == null) {
                contentView.visibility = View.GONE
            } else {
                contentView.visibility = View.VISIBLE
            }
            contentView.setText(value)
        }

    var hint: CharSequence?
        get() = contentView.hint
        set(value) {
            contentView.hint = value
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

    private lateinit var navIconView: ImageView

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditPreference)
        val title = typedArray.getString(R.styleable.EditPreference_title) ?: ""
        val hint = typedArray.getString(R.styleable.EditPreference_hint) ?: ""
        val content = typedArray.getString(R.styleable.EditPreference_text) ?: ""
        val maxLength = typedArray.getInt(R.styleable.EditPreference_maxLength, -1)
        val maxLines = typedArray.getInt(R.styleable.EditPreference_maxLines, -1)
        val icon: Drawable? = typedArray.getDrawable(R.styleable.EditPreference_icon)
        val iconTint: Int = typedArray.getColor(R.styleable.EditPreference_iconTint, -1)
        typedArray.recycle();

        addView(context.linearLayout {
            backgroundResource = R.drawable.bg_ripple
            lparams(matchParent, wrapContent)
            orientation = LinearLayout.VERTICAL

            view {
                backgroundColor = splitColor
            }.lparams(matchParent, 1) {
                marginStart = dip(16)
            }

            linearLayout {
                lparams(matchParent, wrapContent)
                topPadding = dip(8)
                bottomPadding = dip(8)
                leftPadding = dip(16)
                rightPadding = dip(16)
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

                textView {
                    textColor = resources.getColor(android.R.color.black)
                    textSize = 16f
                    text = title
                }.lparams(wrapContent, wrapContent) {
                    rightMargin = dip(16)
                }

                contentView = editText(content) {
                    this.textSize = 16f
                    this.hint = hint
                    if (maxLines != -1) {
                        this.maxLines = maxLines
                    }
                    if (maxLength != -1) {
                        this.filters = arrayOf(InputFilter.LengthFilter(maxLength))
                    }
                    backgroundColorResource = android.R.color.transparent
                }

                setOnClickListener {
                    if (!contentView.hasFocus()) {
                        contentView.requestFocus()
                    }
                    val inputMethodManager = context.inputMethodManager
                    inputMethodManager.showSoftInput(contentView, 0);
                }
            }

            view {
                backgroundColor = splitColor
            }.lparams(matchParent, 1) {
                marginStart = dip(16)
            }
        })


    }

}