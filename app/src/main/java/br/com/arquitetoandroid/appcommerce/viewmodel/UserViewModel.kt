package br.com.arquitetoandroid.appcommerce.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.arquitetoandroid.appcommerce.model.User
import br.com.arquitetoandroid.appcommerce.repository.UsersRespository

class UserViewModel (application: Application) : AndroidViewModel(application) {

    private val usersRespository = UsersRespository(getApplication())

    fun createUser(user: User) = usersRespository.insert(user)
}