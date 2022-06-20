package com.example.peopleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.models.CursoAPIItem
import com.example.models.HistorialAlumno
import com.example.models.Matricula
import com.example.models.Matriculas
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.navigation_header.*

class VerMatriculaFragment : FragmentUtils() {

    var matriculas: Matriculas = Matriculas.instance

    //lateinit var historial: HistorialAlumno
    private var tipoAgregado = 0 //0 -> Agregar, 1 -> Modificar
    lateinit var historial : HistorialAlumno
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var cedula  = arguments?.getString("cedula")

        setToolbarTitle("Ver historial")
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_ver_matricula, container, false)

        var sentPerson  = arguments?.getSerializable("historial")

        if(sentPerson != null){
            tipoAgregado = 1//Modificar
            historial = sentPerson as HistorialAlumno
            view.findViewById<TextView>(R.id.textView_accionPersona).setText("Ver historial")
            view.findViewById<EditText>(R.id.editText_Group).setText(historial.numero_grupo.toString())
            view.findViewById<EditText>(R.id.editText_est).setText(cedula)
            view.findViewById<EditText>(R.id.exitText_Estado).setText(historial.anio_ciclo.toString())
            view.findViewById<EditText>(R.id.exitText_Nota).setText(historial.nota.toString())
            view.findViewById<EditText>(R.id.exitText_Estado3).setText(historial.numero_ciclo)

        }

        /*view.findViewById<Button>(R.id.btn_guardarPersona).setOnClickListener{
            createPerson()
        }*/

        view.findViewById<Button>(R.id.btn_volver).setOnClickListener {
            volver()
        }

        return view
    }

    private fun createPerson(){
        var message:String? = null
        var editTextGroup = view?.findViewById<EditText>(R.id.editText_Group)
        var editTextCed = view?.findViewById<EditText>(R.id.editText_est)
        var editTextNota = view?.findViewById<EditText>(R.id.exitText_Nota)
        var editTextAnio = view?.findViewById<EditText>(R.id.exitText_Estado)
        var editTextNumero = view?.findViewById<EditText>(R.id.exitText_Estado3)

        var group = editTextGroup?.text.toString()
        var cedula = editTextCed?.text.toString()
        var nota = editTextNota?.text.toString()
        var anio = editTextAnio?.text.toString()
        var numero = editTextNumero?.text.toString()

        var image = R.drawable.foto01
/*
        if(tipoAgregado == 0){
            matricula = Matricula(group, cedula, nota.toInt(), estado, ciclo)
            matriculas.addMatricula(matricula)
            message = "Matricula Agregada"
        }
        else{
            matricula.codGrupo = group
            matricula.cedEstudiante = cedula
            matricula.nota = nota.toInt()
            if(matricula.nota < 70){
                matricula.estado = "Reprobado"
            }
            else{
                matricula.estado = "Aprobado"
            }
            matricula.codCiclo = ciclo
            var index = matricula.position as Int
            matriculas.editMatricula(matricula, index)
            message = "Matricula Modificada"
        }
        Snackbar
            .make(view!!, message!!, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()

        if(tipoAgregado == 0){
            editTextGroup?.setText("")
            editTextCed?.setText("")
            editTextNota?.setText("")
            editTextEstado?.setText("")
            editTextCiclo?.setText("")

        }
        else{
            volver()
        }*/
    }
    private fun volver(){
        setToolbarTitle("Matricula")
        changeFragment(HomeFragment())
    }
}

