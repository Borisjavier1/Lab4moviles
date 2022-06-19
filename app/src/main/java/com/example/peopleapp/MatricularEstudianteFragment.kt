package com.example.peopleapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.models.*
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.CountDownLatch


class MatricularEstudianteFragment : Fragment()  {
    var client = OkHttpClient()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var matriculas: Matriculas = Matriculas.instance

        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_matricular_estudiante, container, false)

        var editTextCurso = view.findViewById<EditText>(R.id.editTextCurso)
        var estudiante  = arguments?.getInt("estudiante")
        var ciclo  = arguments?.getInt("ciclo")

        view.findViewById<TextView>(R.id.matricular).setText("Cédula del estudiante a matricular: "+estudiante.toString())

        view.findViewById<Button>(R.id.buttonMatricular).setOnClickListener{
            if(estudiante != null ){
            get(estudiante,editTextCurso.text.toString().toInt())}
          //matriculas.addMatricula(Matricula(editTextCurso.text.toString(), estudiante!!,0,"Reprobado", ciclo!!))
            /*Snackbar
                .make(view!!, "Matricula agregada"!!, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()*/
        }
        view.findViewById<Button>(R.id.atras).setOnClickListener{
            changeFragment(MatricularseFragment())
        }
       return view
    }

    private fun changeFragment(fragment: Fragment){
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()

    }



    fun get(al:Int,gru:Int) {
        // val etLocation = findViewById<EditText>(R.id.etLocation)
        val request = okhttp3.Request.Builder()
            //.url("http://10.0.2.2:28019/api/usuarios")
            .url(getString(R.string.url)+"matricular/"+al+"/"+gru)
            .build()
        var countDownLatch: CountDownLatch = CountDownLatch(1)
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.message.toString())
                countDownLatch.countDown();
                //Toast.makeText(applicationContext,e.message.toString(),Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call, responseHttp: okhttp3.Response) {
                val gson = Gson()
                var valor = responseHttp.body()?.string()
                countDownLatch.countDown();
                println(valor)
               if(valor=="true") {
                   Snackbar
                .make(view!!, "Estudiante matriculado"!!, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
               }
                else{
                   Snackbar
                       .make(view!!, "Error: Estudiante ya matriculado o grupo inválido"!!, Snackbar.LENGTH_LONG)
                       .setAction("Action", null)
                       .show()
               }
            }
        })
        countDownLatch.await();
    }
}