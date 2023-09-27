package com.example.todo.ui.viewmodel

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Locale

class SettingViewModel(var sharedPreferences: SharedPreferences):ViewModel() {
    var language = MutableLiveData<String>()
    var mode = MutableLiveData<Int>()
    var editor = sharedPreferences.edit()

    fun postLanguage(lang:String){
        language.postValue(lang)
        editor.putString("lang",lang).apply()
    }

    fun getLanguage():String{
        val currentlanguage = sharedPreferences.getString("lang",Locale.getDefault().language)
        return currentlanguage!!
    }

    fun postMode(theme:Int){
        mode.postValue(theme)
        editor.putInt("mode",theme).apply()
    }

    fun getMode():Int{
        val currentMode = sharedPreferences.getInt("mode",AppCompatDelegate.getDefaultNightMode())
        return currentMode
    }

}