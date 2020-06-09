package br.com.arquitetoandroid.appcommerce.viewmodel

import android.app.Application
import android.preference.PreferenceManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.arquitetoandroid.appcommerce.model.User
import br.com.arquitetoandroid.appcommerce.model.UserAddress
import br.com.arquitetoandroid.appcommerce.model.UserWithAddresses
import br.com.arquitetoandroid.appcommerce.repository.UsersRepository

class UserViewModel (application: Application) : AndroidViewModel(application) {

    private val usersRepository = UsersRepository(getApplication())

    fun createUser(user: User) = usersRepository.insert(user)

    fun createAddress(userAddress: UserAddress) = usersRepository.insert(userAddress)

    fun updateUser(user: User) = usersRepository.update(user)

    fun updateAddress(userAddress: UserAddress) = usersRepository.update(userAddress)

    fun login(email: String, password: String) : MutableLiveData<User> {
        return MutableLiveData(
            usersRepository.login(email, password).also { user ->
                PreferenceManager.getDefaultSharedPreferences(getApplication()).let {
                    if (user != null)
                        it.edit().putString(USER_ID, user.id).apply()
                }
            }
        )
    }

    fun logout() = PreferenceManager.getDefaultSharedPreferences(getApplication()).let {
        it.edit().remove(USER_ID).apply()
    }

    fun isLogged(): LiveData<UserWithAddresses> = PreferenceManager.getDefaultSharedPreferences(getApplication()).let {
        return usersRepository.loadWithAddresses(it.getString(USER_ID, ""))
    }

    companion object {
        val USER_ID = "USER_ID"
    }
}