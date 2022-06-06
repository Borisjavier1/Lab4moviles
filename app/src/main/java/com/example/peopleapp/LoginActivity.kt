package com.example.peopleapp

import android.accounts.AccountManager.KEY_PASSWORD
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.models.Personas
import com.example.models.Usuarios
import kotlinx.android.synthetic.main.navigation_header.*


class LoginActivity : AppCompatActivity() {

    var personas: Personas = Personas.instance
    var usuarios: Usuarios = Usuarios.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var et_user_name = findViewById(R.id.et_user_name) as EditText
        var et_password = findViewById(R.id.et_password) as EditText
        var btn_reset = findViewById(R.id.btn_reset) as Button
        var btn_submit = findViewById(R.id.btn_submit) as Button

        btn_reset.setOnClickListener {
            // clearing user_name and password edit text views on reset button click
            et_user_name.setText("")
            et_password.setText("")
        }

        // set on-click listener
        btn_submit.setOnClickListener {
            val user_name = et_user_name.text;
            val password = et_password.text;
            //Toast.makeText(this@LoginExample, user_name, Toast.LENGTH_LONG).show()
            if(usuarios.login(user_name.toString(), password.toString())){
                val bundle = Bundle()
                val Login = usuarios.loginP(user_name.toString(), password.toString())
                val i = Intent(this, MainActivity::class.java)

                val pref = applicationContext.getSharedPreferences("Session Data", MODE_PRIVATE)
                val editor: SharedPreferences.Editor = pref.edit()

                if (Login != null) {
                    editor.putString("cedula", Login.cedula)
                    editor.putInt("rol", Login.rol)
                    editor.commit()
                }

                i.putExtra("Login", Login)
                startActivity(i)
                finish()

            }else{
                Toast.makeText(this, "El usuario no se encuentra registrado", Toast.LENGTH_SHORT).show()
            }

        }
    }
}