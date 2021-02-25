package com.example.hw_filimonov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    class Person(val firstName: String, val lastName: String, val age: Int)

    class Repository {
        private val personList: List<Person> = listOf(Person("Nikita", "Filimonov", 26))
    }
}