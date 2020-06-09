package br.com.arquitetoandroid.appcommerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import br.com.arquitetoandroid.appcommerce.model.User
import br.com.arquitetoandroid.appcommerce.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText

class UserRegisterActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var textTitle: TextView
    lateinit var registerName: TextInputEditText
    lateinit var registerEmail: TextInputEditText
    lateinit var registerPassword: TextInputEditText
    lateinit var btnUserRegister: Button

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_register)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        textTitle = findViewById(R.id.toolbar_title)
        textTitle.text = getString(R.string.user_register_title)

        registerName = findViewById(R.id.txt_edit_name)
        registerEmail = findViewById(R.id.txt_edit_email)
        registerPassword = findViewById(R.id.txt_edit_password)

        btnUserRegister = findViewById(R.id.btn_user_register)
        btnUserRegister.setOnClickListener {
            if(validate()) {
                val user = User(
                    name = registerName.text.toString(),
                    email = registerEmail.text.toString(),
                    password = registerPassword.text.toString(),
                    image = "",
                    surname = ""
                )

                userViewModel.createUser(user)
                userViewModel.login(user.email, user.password).observe(this, Observer {
                    finish()
                })
            }
        }
    }

    private fun validate() : Boolean {
        var isValid = true

        registerName.apply {
            if(text.isNullOrEmpty()) {
                error = "Preencha o campo nome."
                isValid = false
            } else {
                error = null
            }
        }
        registerEmail.apply {
            if(text.isNullOrEmpty()) {
                error = "Preencha o campo email."
                isValid = false
            } else {
                error = null
            }
        }
        registerPassword.apply {
            if(text.isNullOrEmpty()) {
                error = "Preencha o campo a senha."
                isValid = false
            } else {
                error = null
            }
        }

        return isValid
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
