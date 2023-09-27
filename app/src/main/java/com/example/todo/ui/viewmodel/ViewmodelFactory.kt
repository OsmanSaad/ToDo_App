package com.example.todo.ui.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class viewmodelFactory(private val sharedPreferences: SharedPreferences):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass == SettingViewModel::class.java)
            return SettingViewModel(sharedPreferences) as T
        throw Throwable("Exception")
    }
}