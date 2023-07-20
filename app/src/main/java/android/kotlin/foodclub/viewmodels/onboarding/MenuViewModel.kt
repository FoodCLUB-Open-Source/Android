package com.example.foodclub.viewmodels.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MenuViewModel : ViewModel() {
    private val _title = MutableLiveData("Menu View")
    val title: LiveData<String> get() = _title
}
