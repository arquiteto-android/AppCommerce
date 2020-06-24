package br.com.arquitetoandroid.appcommerce

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import br.com.arquitetoandroid.appcommerce.viewmodel.UserViewModel

class OrderActivity  : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var textTitle: TextView

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        textTitle = findViewById(R.id.toolbar_title)
        textTitle.text = getString(R.string.order_title)

        userViewModel.isLogged().observe(this, Observer {
            if (it == null) {
                startActivity(Intent(this, UserLoginActivity::class.java))
                finish()
            } else {
                val args = Bundle()
                args.putSerializable("USER", it.user)
                val fragment = OrderFragment()
                fragment.arguments = args
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_order, fragment)
                    .commit()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}