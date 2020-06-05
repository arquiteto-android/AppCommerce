package br.com.arquitetoandroid.appcommerce

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import br.com.arquitetoandroid.appcommerce.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class UserProfileActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var textTitle: TextView
    lateinit var imageProfile: ImageView
    lateinit var photoURI: Uri
    lateinit var userProfileName: TextInputEditText
    lateinit var userProfileSurname: TextInputEditText
    lateinit var userProfileEmail: TextInputEditText
    lateinit var userAddress1: TextInputEditText
    lateinit var userAddressNumber: TextInputEditText
    lateinit var userAddress2: TextInputEditText
    lateinit var userAddressCity: TextInputEditText
    lateinit var userAddressCep: TextInputEditText
    lateinit var userAddressState: Spinner

    private val userViewModel by viewModels<UserViewModel>()

    val REQUEST_TAKE_PHOTO = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        textTitle = findViewById(R.id.toolbar_title)
        textTitle.text = getString(R.string.user_profile_title)

        userProfileName = findViewById(R.id.txt_edit_name)
        userProfileSurname = findViewById(R.id.txt_edit_surname)
        userProfileEmail = findViewById(R.id.txt_edit_email)
        userAddress1 = findViewById(R.id.txt_edit_address)
        userAddress2 = findViewById(R.id.txt_edit_address2)
        userAddressNumber = findViewById(R.id.txt_edit_number)
        userAddressCity = findViewById(R.id.txt_edit_city)
        userAddressCep = findViewById(R.id.txt_edit_cep)
        userAddressState = findViewById(R.id.sp_state)

        imageProfile = findViewById(R.id.iv_profile_image)
        imageProfile.setOnClickListener{ takePicture() }

        val profileImage = PreferenceManager.getDefaultSharedPreferences(this).getString(MediaStore.EXTRA_OUTPUT, null)

        if (profileImage != null) {
            photoURI = Uri.parse(profileImage)
            imageProfile.setImageURI(photoURI)
        } else {
            imageProfile.setImageResource(R.drawable.profile_image)
        }

        userViewModel.isLogged().observe(this, Observer {
            if (it != null) {
                userProfileName.setText(it.user.name)
                userProfileSurname.setText(it.user.surname)
                userProfileEmail.setText(it.user.email)
                it.addresses.first().let { address ->
                    userAddress1.setText(address.addressLine1)
                    userAddress2.setText(address.addressLine2)
                    userAddressNumber.setText(address.number)
                    userAddressCity.setText(address.city)
                    userAddressCep.setText(address.zipCode)
                    resources.getStringArray(R.array.states).asList().indexOf(address.state).let {
                        userAddressState.setSelection(it)
                    }
                }
            } else {
                startActivity(Intent(this, UserLoginActivity::class.java))
                finish()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timesTamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "PROFILE_${timesTamp}",
            ".jpg",
            storageDir
        )
    }

    private fun takePicture() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                 } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    photoURI = FileProvider.getUriForFile(this,
                        "br.com.arquitetoandroid.appcommerce.fileprovider",
                        it)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(intent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        PreferenceManager.getDefaultSharedPreferences(this).apply {
            edit().putString(MediaStore.EXTRA_OUTPUT, photoURI.toString()).apply()
        }

        imageProfile.setImageURI(photoURI)
    }
}
