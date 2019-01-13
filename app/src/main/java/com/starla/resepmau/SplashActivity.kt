package com.starla.resepmau
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    private val mHandler = Handler()
    private val mCallback = Runnable {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        mHandler.postDelayed(mCallback, 2500)
    }
}