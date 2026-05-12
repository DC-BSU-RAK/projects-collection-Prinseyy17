package com.gundambase.portable.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Base activity that applies the selected faction theme
 * before layouts are inflated.
 */
abstract class BaseThemedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(ThemeManager.getActivityTheme(this))

        super.onCreate(savedInstanceState)
    }
}