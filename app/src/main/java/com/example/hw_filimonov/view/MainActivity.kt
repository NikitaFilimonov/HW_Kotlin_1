package com.example.hw_filimonov.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hw_filimonov.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MovieFragment.newInstance())
                .commitNow()
        }
    }
}