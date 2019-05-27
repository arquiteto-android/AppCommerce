package br.com.arquitetoandroid.appcommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class UserLoginActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var textTitle: TextView
    lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        textTitle = findViewById(R.id.toolbar_title)
        textTitle.text = getString(R.string.user_login_title)

        btnRegister = findViewById(R.id.btn_login_register)
        btnRegister.setOnClickListener {
            val intent = Intent(this, UserRegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
