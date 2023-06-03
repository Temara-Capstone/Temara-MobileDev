package com.team.temara.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.core.content.ContextCompat
import com.team.temara.R

class ArtikelEditText : CustomEditText {

    private lateinit var iconDrawable: Drawable
    private lateinit var filterDrawable: Drawable

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
        iconDrawable = ContextCompat.getDrawable(context, R.drawable.search) as Drawable
        filterDrawable = ContextCompat.getDrawable(context, R.drawable.filter) as Drawable
        setEditTextDrawable(iconDrawable, null,  filterDrawable, null)
        compoundDrawablePadding = 20

        hint = resources.getString(R.string.desc_artikel)


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