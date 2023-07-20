package com.example.foodclub.viewmodels.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupViewModel : ViewModel() {
    private val _title = MutableLiveData("SignupViewModel View")
    val title: LiveData<String> get() = _title
}
