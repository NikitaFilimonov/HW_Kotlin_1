package com.example.hw_filimonov.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hw_filimonov.R
import com.example.hw_filimonov.view.details.MovieFragment
import com.example.hw_filimonov.view.list.ListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ListFragment.newInstance())
                .commitAllowingStateLoss()
        }
    }
}
