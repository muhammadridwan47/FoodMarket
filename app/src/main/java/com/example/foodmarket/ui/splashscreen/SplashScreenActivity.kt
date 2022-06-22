package com.example.foodmarket.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodmarket.R
import com.example.foodmarket.ui.auth.AuthActivity
import java.util.*
import kotlin.concurrent.timerTask

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Timer().schedule(timerTask {
            startActivity(Intent(this@SplashScreenActivity, AuthActivity::class.java))
            finish()
        }, 2000)
    }
}