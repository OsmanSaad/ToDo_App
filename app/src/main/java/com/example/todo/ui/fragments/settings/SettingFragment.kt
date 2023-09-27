package com.example.todo.ui.fragments.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.todo.R
import com.example.todo.databinding.FragmentSettingBinding
import com.example.todo.ui.viewmodel.SettingViewModel
import com.example.todo.ui.viewmodel.viewmodelFactory
import java.util.Locale


class SettingFragment : Fragment() {
   lateinit var viewmodelFactory: viewmodelFactory
   lateinit var settingViewModel: SettingViewModel
   lateinit var binding:FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var sharedPreferences = requireActivity().getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        viewmodelFactory = viewmodelFactory(sharedPreferences)
        settingViewModel = ViewModelProvider(this,viewmodelFactory).get(SettingViewModel::class.java)
        binding.lifecycleOwner = this

        languageSelection()

        modeSelection()

    }

    private fun manageLang(local:String){
        var locale = LocaleListCompat.create(Locale(local))
        settingViewModel.postLanguage(local)
        AppCompatDelegate.setApplicationLocales(locale)
    }
    private fun languageSelection(){
        binding.dropdownLanguage.onItemSelectedListener = object :OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position){
                    1->{
                        val currentsystem = Resources.getSystem().configuration.locales[0].language
                        manageLang(currentsystem)
                    }
                    2->{
                        if(settingViewModel.getLanguage()!="en")
                            manageLang("en")
                    }
                    3->{
                        if(settingViewModel.getLanguage()!="ar")
                            manageLang("ar")
                    }
                }


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    private fun modeSelection(){
        binding.dropdownMode.onItemSelectedListener = object :OnItemSelectedListener{
            @SuppressLint("WrongConstant")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position){
                    1->{
                        settingViewModel.postMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }
                    2->{
                        settingViewModel.postMode(AppCompatDelegate.MODE_NIGHT_NO)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    3->{
                        settingViewModel.postMode(AppCompatDelegate.MODE_NIGHT_YES)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

    }

}