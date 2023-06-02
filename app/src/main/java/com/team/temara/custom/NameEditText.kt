package com.team.temara.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.team.temara.R

class NameEditText : CustomEditText {

    private lateinit var iconDrawable: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {

        iconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_user) as Drawable
        setEditTextDrawable(iconDrawable)
        compoundDrawablePadding = 20
        hint = resources.getString(R.string.name_desc)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!text.isNullOrBlank()) {
                    error = if (text!!.length <= 2) {
                        resources.getString(R.string.name_minimum)
                    } else {
                        null
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (text.isNullOrBlank()) {
                    error = resources.getString(R.string.name_empty)
                }
            }
        })
    }

    private fun setEditTextDrawable(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }
}
