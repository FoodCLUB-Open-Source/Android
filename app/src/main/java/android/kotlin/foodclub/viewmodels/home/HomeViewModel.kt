package com.example.foodclub.viewmodels.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _title = MutableLiveData("HomeViewModel View")
    val title: LiveData<String> get() = _title
}
