package br.com.arquitetoandroid.appcommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import br.com.arquitetoandroid.appcommerce.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText

class UserLoginActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var textTitle: TextView
    lateinit var btnRegister: Button
    lateinit var btnUserLogin: Button
    lateinit var loginEmail: TextInputEditText
    lateinit var loginPassword: TextInputEditText

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        textTitle = findViewById(R.id.toolbar_title)
        textTitle.text = getString(R.string.user_login_title)

        loginEmail = findViewById(R.id.txt_edit_login_email)
        loginPassword = findViewById(R.id.txt_edit_login_password)

        btnUserLogin = findViewById(R.id.btn_user_login)
        btnUserLogin.setOnClickListener {
            userViewModel.login(loginEmail.text.toString(), loginPassword.text.toString()).observe(this, Observer {

                if(it == null)
                    Toast.makeText(applicationContext, getString(R.string.login_message), Toast.LENGTH_SHORT).show()
                else
                    finish()

            })
        }

        btnRegister = findViewById(R.id.btn_login_register)
        btnRegister.setOnClickListener {
            val intent = Intent(this, UserRegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
