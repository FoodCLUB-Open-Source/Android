package com.example.foodclub.viewmodels.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _title = MutableLiveData("LoginViewModel View")
    val title: LiveData<String> get() = _title
}
