package com.example.todo.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.todo.R
import com.example.todo.databinding.FragmentBottomSheetBinding
import com.example.todo.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {

    lateinit var binding:FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting,container,false)
        return binding.root
    }



}