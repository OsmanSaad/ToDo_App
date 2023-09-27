package com.example.todo.ui

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

class MyApp:Application() {
    lateinit var sharedprefrences:SharedPreferences
    override fun onCreate() {
        super.onCreate()
        sharedprefrences = getSharedPreferences("AppSettings",MODE_PRIVATE)
        applayAppTheme()
        applayAppLanguage()
    }
    fun applayAppLanguage(){
        val lang = sharedprefrences.getString("lang",Locale.getDefault().language)
        val locale = LocaleListCompat.create(Locale(lang))
        AppCompatDelegate.setApplicationLocales(locale)
    }

    fun applayAppTheme(){
        val mode = sharedprefrences.getInt("mode",AppCompatDelegate.getDefaultNightMode())
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}