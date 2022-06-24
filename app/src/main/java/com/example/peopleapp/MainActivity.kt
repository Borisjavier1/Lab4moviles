package com.example.peopleapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.models.ProfesorAPIItem
import com.example.models.Usuario
import com.example.models.UsuarioAPIItem
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.CountDownLatch


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var guia: Int = 0
    var client = OkHttpClient()
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
        val personArg =  bundle?.getSerializable("Login") as UsuarioAPIItem

        val navigationView = findViewById<NavigationView>(R.id.nav_menu)
        val header = navigationView?.getHeaderView(0)
        header?.findViewById<TextView>(R.id.nav_header_nombre)?.text = personArg.cedula

        changeFragment(HomeFragment())
        if(personArg.rol==2) {
            get(personArg.cedula)
        }
        if(personArg.rol!=3){
            val navAk7: NavigationView = findViewById(R.id.nav_menu) as NavigationView
            val nav_per: Menu = navAk7.getMenu()
            nav_per.findItem(R.id.nav_item_career).setVisible(false)
            nav_per.findItem(R.id.nav_item_courses).setVisible(false)
            nav_per.findItem(R.id.nav_item_groups).setVisible(false)
            nav_per.findItem(R.id.nav_item_cycle).setVisible(false)
            nav_per.findItem(R.id.nav_item_professors).setVisible(false)
            nav_per.findItem(R.id.nav_item_students).setVisible(false)
            nav_per.findItem(R.id.nav_item_admins).setVisible(false)
            nav_per.findItem(R.id.nav_item_matricula).setVisible(false)

        }
        if(guia==1){
            val navAk7: NavigationView = findViewById(R.id.nav_menu) as NavigationView
            val nav_per: Menu = navAk7.getMenu()
            nav_per.findItem(R.id.nav_item_matricula).setVisible(true)
        }

    }




    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        drawerLayout.closeDrawer(GravityCompat.START)
        when(item.itemId){
            R.id.nav_item_home -> {
                val sp: SharedPreferences
                var myContext = this
                sp = myContext.getSharedPreferences("Session Data", Context.MODE_PRIVATE)
                var rol = sp.getInt("rol", 3)
                if(rol==2) {
                    setToolbarTitle("Grupos a cargo")
                }
                if(rol==1) {
                    setToolbarTitle("Historial académico")
                }
                changeFragment(HomeFragment())
            }
            R.id.nav_item_career -> {
                setToolbarTitle("Carreras")
                changeFragment(CareerFragment())
            }
            R.id.nav_item_courses -> {
                setToolbarTitle("Cursos")
                changeFragment(CourseFragment())
            }
            R.id.nav_item_groups -> {
                setToolbarTitle("Oferta acádemica")
                changeFragment(OfertaFragment())
            }
            R.id.nav_item_cycle -> {
                setToolbarTitle("Ciclos")
                changeFragment(CycleFragment())
            }
            R.id.nav_item_professors -> {
                setToolbarTitle("Profesores")
                changeFragment(ProfessorFragment())
            }
            R.id.nav_item_students -> {
                setToolbarTitle("Estudiantes")
                changeFragment(StudentFragment())
            }
            R.id.nav_item_matricula -> {
                setToolbarTitle("Matrícula")
                changeFragment(MatricularseFragment())
            }
            R.id.nav_item_admins -> {
                setToolbarTitle("Administradores")
                changeFragment(AdministradorFragment())
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

    fun get(cedula:String) {
        // val etLocation = findViewById<EditText>(R.id.etLocation)
        val request = Request.Builder()
            //.url("http://10.0.2.2:28019/api/usuarios")
            .url(getString(R.string.url)+"buscarProfesorCedula/"+cedula)
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
                var entidadJson = gson?.fromJson<ProfesorAPIItem>(valor, ProfesorAPIItem::class.java)
                guia = entidadJson.guia
                countDownLatch.countDown();

                //Toast.makeText(applicationContext,valor.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        countDownLatch.await();
    }

}