package com.example.peopleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.models.UsuarioAPIItem

class Paso : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paso)

        var bundle = intent.extras
        val id =  bundle?.getInt("id")
        val k = intent.getIntExtra("id", 0)
        print("id mieo es: ----> " + k)


        supportFragmentManager.beginTransaction().replace(R.id.frameLayout,CourseFragment()).commit()
    }

}