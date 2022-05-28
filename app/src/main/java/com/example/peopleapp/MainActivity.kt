package com.example.peopleapp

import android.content.Context
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.models.Persona
import com.example.models.Usuario
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        nav_menu.setNavigationItemSelectedListener(this)

        var bundle = intent.extras
        val personArg =  bundle?.getSerializable("Login") as Usuario

        val navigationView = findViewById<NavigationView>(R.id.nav_menu)
        val header = navigationView?.getHeaderView(0)
        header?.findViewById<TextView>(R.id.nav_header_nombre)?.text = personArg.cedula

        changeFragment(HomeFragment())
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when(item.itemId){
            R.id.nav_item_home -> {
                setToolbarTitle("Home")
                changeFragment(HomeFragment())
            }
            R.id.nav_item_about -> {
                setToolbarTitle("About")
                changeFragment(AboutFragment())
            }
            R.id.nav_item_pesonas -> {
                setToolbarTitle("Personas")
                changeFragment(PersonasFragment())
            }
            R.id.nav_item_logout ->{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return true
    }

    private fun setToolbarTitle(title:String){
        supportActionBar?.title = title
    }
    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()

    }


}