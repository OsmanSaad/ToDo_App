package com.example.todo.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todo.databinding.ActivityTodoDeatailsBinding


class todoDeatailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityTodoDeatailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoDeatailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}