package com.example.peopleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment


class OfertaFragment : Fragment()  {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_oferta, container, false)

        var editTextCarrera = view.findViewById<EditText>(R.id.editTextCarrera)
        var editTextCiclo = view.findViewById<EditText>(R.id.editTextCiclo)
        var botton = view.findViewById<Button>(R.id.buttonExaminar)

        view.findViewById<Button>(R.id.buttonExaminar).setOnClickListener{
            //Toast.makeText(activity,editTextCarrera.text,Toast.LENGTH_SHORT).show();
            val datosAEnviar = Bundle()
            datosAEnviar.putString("carrera",editTextCarrera.text.toString())
            datosAEnviar.putString("ciclo",editTextCarrera.text.toString())
            val fragmento: Fragment = CourseOfertaFragment()
            fragmento.setArguments(datosAEnviar);
            changeFragment(fragmento)
        }



        return view

    }

    private fun changeFragment(fragment: Fragment){
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()

    }
}