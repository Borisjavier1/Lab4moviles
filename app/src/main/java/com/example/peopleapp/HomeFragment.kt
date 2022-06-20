package com.example.peopleapp

import android.R.attr.password
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment


class HomeFragment : Fragment()  {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val sp: SharedPreferences
        var myContext = activity!!
        sp = myContext.getSharedPreferences("Session Data", Context.MODE_PRIVATE)
        var ced = sp.getString("cedula", "")
        var rol = sp.getInt("rol", 0)

        if(rol==2){

            getActivity()?.setTitle("Grupos a cargo");
            changeFragment(GroupTeacherFragment())
        }
        if(rol==1){
            getActivity()?.setTitle("Historial académico");
            changeFragment(HistorialEstudiantesFragments())
        }

        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    private fun changeFragment(fragment: Fragment){
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()

    }
}