package com.example.todo.domain

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.example.todo.R
import java.util.Locale

object DataValidation {

    fun validateData(title:String?
                     ,description:String?
                     ,titleLiveData: MutableLiveData<String>,
                     descriptionLive:MutableLiveData<String>):Boolean{
        if(title.isNullOrBlank()){
                titleLiveData.postValue(Constatns.INVALID_TITLE)
            return false
        }
        else{
            titleLiveData.postValue(Constatns.VALID)
        }
        if(description.isNullOrBlank()){
                descriptionLive.postValue(Constatns.INVALID_DESCRIPTION)
            return false
        }
        else{
            descriptionLive.postValue(Constatns.VALID)
        }
        return true
    }
}