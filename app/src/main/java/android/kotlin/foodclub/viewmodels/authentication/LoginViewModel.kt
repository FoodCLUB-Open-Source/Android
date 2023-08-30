//package com.example.foodclub.viewmodels.authentication
//
//import android.kotlin.foodclub.api.authentication.SignUpUserRepository
//import android.kotlin.foodclub.api.authentication.UserSignUpInformation
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.launch
//
//class LoginViewModel : ViewModel() {
//    private val _title = MutableLiveData("LoginViewModel View")
//    private val repository = SignUpUserRepository()
//
//    // need to change the List type since we're not doing a GET request, no need for a whole List when signup
//    private val _user = MutableLiveData<List<UserSignUpInformation>>()
//    val user: LiveData<List<UserSignUpInformation>> = _user
//
//    fun fetchUserSignUp() {
//        viewModelScope.launch {
//            try {
//                // We'd need to put parameters like username, email and password
//                val currentUser = repository.postUser()
//                _user.value = currentUser
//            } catch (e: Exception) {
//                // Handle error
//            }
//        }
//    }
//
//    val title: LiveData<String> get() = _title
//}
