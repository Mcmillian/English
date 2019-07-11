@file:Suppress("DEPRECATION")

package com.szbitnet.common

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.jetbrains.anko.*
import org.jetbrains.anko.internals.AnkoInternals

class BottomSheetDialogBuilder(private val context: Context) {
    private val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    private lateinit var titleTextView: TextView
    private lateinit var messageTextView: TextView
    private lateinit var DoNotRemindAgainCheckBox: CheckBox
    private lateinit var positiveButton: TextView
    private lateinit var negativeButton: TextView

    private val contentView = context.linearLayout {
        backgroundColor = android.R.color.white
        gravity = android.view.Gravity.CENTER_HORIZONTAL
        orientation = LinearLayout.VERTICAL

        titleTextView = textView {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            }
            textColor = Color.BLACK
            textSize = 20F
            visibility = View.GONE
        }.lparams {
            leftMargin = dip(20)
            rightMargin = dip(20)
            topMargin = dip(20)
        }

        messageTextView = textView {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            }
            textColor = Color.BLACK
            textSize = 18F
            visibility = View.GONE
        }.lparams {
            leftMargin = dip(20)
            rightMargin = dip(20)
            topMargin = dip(16)
            bottomMargin = dip(16)
        }

        DoNotRemindAgainCheckBox = checkBox("不再提示") {
            textColor = 0x00b0ff
            visibility = View.GONE
        }.lparams {
            bottomMargin = dip(8)
        }

        view {
            backgroundColor = Color.parseColor("#EEEEEE")
        }.lparams(matchParent, 1) {
            leftMargin = dip(12)
            rightMargin = dip(12)
        }

        positiveButton = textView {
            padding = dip(10)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            }
            textSize = 19F
            textColor = 0x00b0ff
        }.lparams(matchParent)

        view {
            backgroundColor = Color.parseColor("#EEEEEE")
        }.lparams(matchParent, 1) {
            leftMargin = dip(12)
            rightMargin = dip(12)
        }

        negativeButton = textView {
            padding = dip(10)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            }
            textSize = 19F
            textColor = android.R.color.darker_gray
            visibility = View.GONE
        }.lparams(matchParent)
    }


    var canceledOnTouchOutside: Boolean = false
    var canceledOnTouchBackKey: Boolean = false
    var title: String
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = AnkoInternals.noGetter()
        set(value) {
            titleTextView.text = value
            titleTextView.visibility = View.VISIBLE
        }

    var titleTextSize: Float
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = AnkoInternals.noGetter()
        set(value) {
            titleTextView.textSize = value
        }

    var titleTextColor: Int
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = AnkoInternals.noGetter()
        set(value) {
            titleTextView.textColor = value
        }

    var message: String
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = AnkoInternals.noGetter()
        set(value) {
            messageTextView.text = value
            messageTextView.visibility = View.VISIBLE
        }

    var messageTextSize: Float
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = AnkoInternals.noGetter()
        set(value) {
            messageTextView.textSize = value
        }

    var messageTextAlignment: Int
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = AnkoInternals.noGetter()
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        set(value) {
            messageTextView.textAlignment = value
        }

    var messageTextColor: Int
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = AnkoInternals.noGetter()
        set(value) {
            messageTextView.textColor = value
        }

    fun checkBox(checkBoxText: String, onCheckedChange: ((Boolean) -> Unit)?) {
        DoNotRemindAgainCheckBox.visibility = View.VISIBLE
        DoNotRemindAgainCheckBox.text = checkBoxText
        DoNotRemindAgainCheckBox.setOnCheckedChangeListener { _, isChecked ->
            onCheckedChange?.invoke(isChecked)
        }
    }

    fun checkBox(checkBoxTextResource: Int, onCheckedChange: ((Boolean) -> Unit)?) {
        checkBox(context.resources.getString(checkBoxTextResource), onCheckedChange)
    }

    fun firstButton(buttonText: String, @ColorInt textColor: Int = Color.parseColor("#3CA0CB"), onClicked: (() -> Unit)? = null) {
        positiveButton.visibility = View.VISIBLE
        positiveButton.text = buttonText
        positiveButton.textColor = textColor
        positiveButton.setOnClickListener {
            bottomSheetDialog.cancel()
            onClicked?.invoke()
        }
    }

    fun firstButton(buttonTextResource: Int, @ColorRes textColorResource: Int = 0, onClicked: (() -> Unit)? = null) {
        firstButton(context.resources.getString(buttonTextResource), context.resources.getColor(textColorResource), onClicked)
    }


    fun secondButton(buttonText: String, @ColorInt textColor: Int = context.resources.getColor(android.R.color.darker_gray), onClicked: (() -> Unit)? = null) {
        negativeButton.visibility = View.VISIBLE
        negativeButton.text = buttonText
        negativeButton.textColor = textColor
        negativeButton.setOnClickListener {
            bottomSheetDialog.cancel()
            onClicked?.invoke()
        }
    }

    fun secondButton(buttonTextResource: Int, @ColorRes textColorResource: Int = android.R.color.darker_gray, onClicked: (() -> Unit)? = null) {
        secondButton(context.resources.getString(buttonTextResource), context.resources.getColor(textColorResource), onClicked)
    }

    fun build(): BottomSheetDialog {
        return bottomSheetDialog.apply {
            setContentView(contentView)
            setCanceledOnTouchOutside(canceledOnTouchOutside)
            setCancelable(canceledOnTouchBackKey)
        }
    }
}

fun Context.bottomAlert(init: BottomSheetDialogBuilder.() -> Unit): BottomSheetDialog {
    return BottomSheetDialogBuilder(this).apply(init).build()
}