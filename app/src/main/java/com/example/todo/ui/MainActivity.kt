package com.example.todo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.data.database.todo.MyDatabase
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.ui.fragments.SettingFragment
import com.example.todo.ui.fragments.bottomSheetFragment
import com.example.todo.ui.fragments.todoList.TodoListFragment


class MainActivity : AppCompatActivity() {
    var todoListFragment = TodoListFragment()
    var settingFragment = SettingFragment()
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MyDatabase.getInstance(this)

        setupBottomNav()



    }

    fun onclickAddTask(view: View){
        val bottomSheetFragment = bottomSheetFragment()
        bottomSheetFragment.show(supportFragmentManager,"")
    }

    private fun pushFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .commit()
    }

    private fun setupBottomNav(){
        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.todo_list ->{
                    pushFragment(todoListFragment)
                    true
                }
                R.id.settings ->{
                    pushFragment(settingFragment)
                    true
                }
                else ->false
            }
        }
        binding.bottomNav.selectedItemId = R.id.todo_list
    }
}