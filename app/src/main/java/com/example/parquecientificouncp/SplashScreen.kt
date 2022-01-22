package com.example.parquecientificouncp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

class SplashScreen : AppCompatActivity() {
    lateinit var handle:Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryDark)

        val textSplash = findViewById<TextView>(R.id.TextSplashScreen)
        textSplash.typeface = ResourcesCompat.getFont(this,R.font.sparta_bold)
        handle = Handler()
        handle.postDelayed({
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        },4000)
    }
}