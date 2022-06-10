package com.example.peopleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.models.Matricula
import com.example.models.Matriculas
import com.google.android.material.snackbar.Snackbar


class MatricularEstudianteFragment : Fragment()  {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var matriculas: Matriculas = Matriculas.instance

        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_matricular_estudiante, container, false)

        var editTextCurso = view.findViewById<EditText>(R.id.editTextCurso)
        var estudiante  = arguments?.getString("estudiante")
        var ciclo  = arguments?.getString("ciclo")

        view.findViewById<TextView>(R.id.matricular).setText("Cédula del estudiante a matricular: "+estudiante.toString())

        view.findViewById<Button>(R.id.buttonMatricular).setOnClickListener{
          matriculas.addMatricula(Matricula(editTextCurso.text.toString(), estudiante!!,0,"Reprobado", ciclo!!))
            Snackbar
                .make(view!!, "Matricula agregada"!!, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }
        view.findViewById<Button>(R.id.atras).setOnClickListener{
            changeFragment(MatricularseFragment())
        }
       return view
    }

    private fun changeFragment(fragment: Fragment){
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()

    }
}