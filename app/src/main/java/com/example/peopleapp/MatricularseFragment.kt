package com.example.peopleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.models.Ciclos


class MatricularseFragment : Fragment()  {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_matricularse, container, false)
        var ciclos: Ciclos = Ciclos.instance

        var editTextEst = view.findViewById<EditText>(R.id.editTextCarrera)
        var editTextCiclo = view.findViewById<EditText>(R.id.editTextCurso)
        var botton = view.findViewById<Button>(R.id.buttonMatricular)

        view.findViewById<Button>(R.id.buttonMatricular).setOnClickListener{
           // Toast.makeText(activity,editTextEst.text,Toast.LENGTH_SHORT).show();
            //Toast.makeText(activity,editTextCiclo.text,Toast.LENGTH_SHORT).show();
            val datosAEnviar = Bundle()
            datosAEnviar.putInt("estudiante",editTextEst.text.toString().toInt())
            datosAEnviar.putInt("ciclo",editTextCiclo.text.toString().toInt())
            var ciclo = ciclos.getCiclo(editTextCiclo.text.toString().toInt())
            if(ciclo?.actual == 1) {
                val fragmento: Fragment = ListaMatriculaFragment()
                fragmento.setArguments(datosAEnviar);
                changeFragment(fragmento)
            }else{
                val fragmento: Fragment = ListaMatriculaFragment2()
                fragmento.setArguments(datosAEnviar);
                changeFragment(fragmento)
            }
        }



        return view

    }

    private fun changeFragment(fragment: Fragment){
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()

    }
}