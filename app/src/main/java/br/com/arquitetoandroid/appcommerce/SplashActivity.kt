package br.com.arquitetoandroid.appcommerce

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val intent: Intent = Intent(this, MainActivity::class.java)

        Handler().postDelayed(Runnable {

            startActivity(intent)
            finish()

        }, 3000)
    }
}
