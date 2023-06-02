package com.team.temara.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.team.temara.R

class PasswordEditText : CustomEditText {

    private lateinit var iconDrawable: Drawable
    private lateinit var showPasswordDrawable: Drawable
    private lateinit var hidePasswordDrawable: Drawable
    private var isPasswordVisible: Boolean = false

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
        iconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_password) as Drawable
        showPasswordDrawable = ContextCompat.getDrawable(context, R.drawable.ic_show_password) as Drawable
        hidePasswordDrawable = ContextCompat.getDrawable(context, R.drawable.ic_hide_password) as Drawable
        setEditTextDrawable(iconDrawable, null,  hidePasswordDrawable, null)
        compoundDrawablePadding = 20

        hint = resources.getString(R.string.password)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                if (!text.isNullOrBlank()) {
//                    error = if (text!!.length <= 7) {
//                        resources.getString(R.string.password_minimum)
//                    } else {
//                        null
//                    }
//                }
            }

            override fun afterTextChanged(s: Editable) {
//                if (text.isNullOrBlank()) {
//                    error = resources.getString(R.string.password_empty)
//                }
            }
        })

        setOnTouchListener { v, event ->
            val drawableRight = compoundDrawablesRelative[2]
            if (event.action == MotionEvent.ACTION_UP && drawableRight != null) {
                val touchableWidth = width - paddingRight - drawableRight.intrinsicWidth
                if (event.rawX >= touchableWidth) {
                    togglePasswordVisibility()
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }
    }

    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
        if (isPasswordVisible) {
            setEditTextDrawable(
                iconDrawable,
                null,
                showPasswordDrawable,
                null
            )
            transformationMethod = null
        } else {
            setEditTextDrawable(
                startOfTheText = iconDrawable,
                endOfTheText = hidePasswordDrawable
            )
            transformationMethod = PasswordTransformationMethod.getInstance()
        }
        setSelection(text?.length ?: 0)
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