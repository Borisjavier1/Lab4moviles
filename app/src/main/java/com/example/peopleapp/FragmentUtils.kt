package com.example.peopleapp

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class FragmentUtils : Fragment(){
    fun changeFragment(fragment: Fragment? = null, fragmentUtils: FragmentUtils? = null){
        if (fragment != null)
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        else
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container, fragmentUtils!!).commit()
    }
    fun setToolbarTitle(title:String){
        (activity as AppCompatActivity).supportActionBar?.title = title
    }
}