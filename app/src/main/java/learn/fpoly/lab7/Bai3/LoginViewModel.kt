package learn.fpoly.lab7.Bai3

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel:ViewModel() {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _rememberMe = MutableLiveData<String>()
    val rememberMe: LiveData<String> = _rememberMe

    private val _isAuthenticated = MutableLiveData<String>()
    val isAuthenticated: LiveData<String> = _isAuthenticated

    fun login (username: String, password: String, rememberMe: Boolean){
        if(username.equals("admin") && password.equals("123")){
            _isAuthenticated.postValue("true")
        }else{
            _isAuthenticated.postValue("false")
        }
    }
    @SuppressLint("NullSafeMutableLiveData")
    fun resetAuthentication(){
        _isAuthenticated.value = null
    }
}