package com.gundambase.portable.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.gundambase.portable.R

/**
 * SplashActivity — shown briefly on launch.
 *
 * The visual appearance is driven by @style/Theme.GundamBase.Splash in themes.xml,
 * which sets the window background to @drawable/launch_screen_graphic.
 * The activity_splash layout provides an optional text overlay on top of that background.
 *
 * To use a fully custom graphic, replace @drawable/launch_screen_graphic with your asset
 * (see the drawable file for density instructions) and optionally remove the layout overlay.
 */
class SplashActivity : AppCompatActivity() {

    companion object {
        private const val SPLASH_DELAY_MS = 1800L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_DELAY_MS)
    }
}
