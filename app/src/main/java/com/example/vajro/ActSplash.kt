package com.example.vajro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class ActSplash : AppCompatActivity() {

    val SPLASH_TIME_OUT = 1000

    var i: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act_splash)
        i = Intent(this@ActSplash, MainActivity::class.java)
        Handler().postDelayed({
            startActivity(i)
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }
}