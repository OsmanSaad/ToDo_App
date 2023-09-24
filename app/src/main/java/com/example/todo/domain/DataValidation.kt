package com.example.todo.domain

import androidx.lifecycle.MutableLiveData

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