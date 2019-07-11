package com.zzmetro.settingpreferences

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.content.res.TypedArray
import android.widget.Switch
import org.jetbrains.anko.*


class SwitchPreference @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var switch: Switch
    private var onSwitch: ((isChecked: Boolean) -> Unit)? = null

    var isChecked: Boolean
        set(value) {
            switch.isChecked = value
        }
        get() = switch.isChecked

    fun onSwitch(action: (isChecked: Boolean) -> Unit) {
        this.onSwitch = action
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchPreference)
        val title = typedArray.getString(R.styleable.SwitchPreference_title) ?: ""
        val summary = typedArray.getString(R.styleable.SwitchPreference_summary)
        val icon = typedArray.getDrawable(R.styleable.SwitchPreference_icon)
        val checked = typedArray.getBoolean(R.styleable.SwitchPreference_checked, false)
        typedArray.recycle();

        addView(context.linearLayout {
            lparams(matchParent, wrapContent)
            padding = dip(16)
            gravity = android.view.Gravity.CENTER_VERTICAL
            orientation = LinearLayout.HORIZONTAL

            linearLayout {
                orientation = LinearLayout.VERTICAL
                textView {
                    textColor = resources.getColor(android.R.color.black)
                    textSize = 16f
                    text = title
                }
                val summaryTextView = textView {
                    text = summary
                }.lparams {
                    topMargin = dip(8)
                }
                summaryTextView.visibility = if (summary != null) View.VISIBLE else View.GONE
            }.lparams(0, wrapContent, 1f) {
                rightMargin = dip(16)
            }

            val imageView = imageView(icon).lparams(dip(16), dip(16)) {
                leftMargin = dip(16)
            }
            imageView.visibility = if (icon != null) View.VISIBLE else View.GONE

            switch = switch {
                this.isChecked = checked
                setOnCheckedChangeListener { _, isChecked ->
                    onSwitch?.invoke(isChecked)

                }
            }

            setOnClickListener {
                switch.isChecked = !switch.isChecked
            }
        })
    }


}