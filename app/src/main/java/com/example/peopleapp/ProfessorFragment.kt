package com.example.peopleapp

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.models.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class ProfessorFragment : FragmentUtils(){
    private lateinit var appBarConfiguration: AppBarConfiguration
    var profesores: Profesores = Profesores.instance

    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: RecyclerView_Adapter5
    lateinit var profesor: ProfesorAPIItem
    var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_cycle, container, false)

        val searchIcon = view.findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.BLACK)


        val cancelIcon = view.findViewById<ImageView>(R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.BLACK)


        val textView = view.findViewById<TextView>(R.id.search_src_text)
        textView.setTextColor(Color.BLACK)

        recyclerViewElement = view.findViewById(R.id.recycleView)
        recyclerViewElement.layoutManager = LinearLayoutManager(recyclerViewElement.context)
        recyclerViewElement.setHasFixedSize(true)

        view.findViewById<SearchView>(R.id.person_search)
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adaptador.filter.filter(newText)

                    return false
                }
            })

        getListOfPersons()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {


            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition: Int = viewHolder.adapterPosition
                val toPosition: Int = target.adapterPosition

                Collections.swap(profesores.getProfesor(), fromPosition, toPosition)

                recyclerViewElement.adapter?.notifyItemMoved(fromPosition, toPosition)

                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                position = viewHolder.adapterPosition

                if (direction == ItemTouchHelper.LEFT) {//Delete

                    var index = getIndex(position)
                    //profesores.deleteProfesor(index)
                    delete(index)

                    recyclerViewElement.adapter?.notifyItemRemoved(position)

                    Snackbar.make(recyclerViewElement, profesor.nombre + " eliminado/a", Snackbar.LENGTH_LONG).setAction("Undo") {
                        profesores.getProfesor().add(position, profesor)
                        recyclerViewElement.adapter?.notifyItemInserted(position)
                    }.show()

                    adaptador = RecyclerView_Adapter5(profesores.getProfesor())
                    recyclerViewElement.adapter = adaptador
                    changeFragment(ProfessorFragment())

                } else { //Edit
                    profesor = ProfesorAPIItem(
                        profesores.getProfesor()[position].cedula,
                        profesores.getProfesor()[position].email,
                        profesores.getProfesor()[position].guia,
                        profesores.getProfesor()[position].id,
                        profesores.getProfesor()[position].nombre,
                        profesores.getProfesor()[position].telefono
                    )


                    var index = getIndex(position)
                    profesor.position = index;

                    var bundle = Bundle()
                    bundle.putSerializable("profesor", profesor)

                    var editFragment = CreateTeacherFragment()
                    editFragment.arguments = bundle

                    setToolbarTitle("Editar Profesor")
                    changeFragment(fragmentUtils = editFragment)
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    this@ProfessorFragment.context,
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@ProfessorFragment.context!!, R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            this@ProfessorFragment.context!!,
                            R.color.green
                        )
                    )
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                    .create()
                    .decorate()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewElement)

        val add: FloatingActionButton = view.findViewById(R.id.add)
        add.setOnClickListener { view ->
            changeFragment(CreateTeacherFragment())
        }
        return view;
    }
    private fun getListOfPersons() {
        val Nprofesores = ArrayList<ProfesorAPIItem>()
        for (p in profesores.getProfesor()) {
            Nprofesores.add(p)
        }
        adaptador = RecyclerView_Adapter5(Nprofesores)
        recyclerViewElement.adapter = adaptador
    }
    private fun getIndex(index: Int): Int{
        var index = index
        var adapterItems = adaptador.itemsList
        var listaGrupos = profesores.getProfesor()

        profesor = adapterItems?.get(index)!!

        index = listaGrupos.indexOfFirst {
            it.nombre == profesor.nombre
        }

        return profesor.id
    }
    @SuppressLint("SetTextI18n")
    fun delete(id: Int) {

        val jsonObject = JSONObject()
        jsonObject.put("carrera",21)
        jsonObject.put("cedula",705)
        jsonObject.put("email","lslsl@gmail.com")
        jsonObject.put("fecha_nac","22-22-22")
        jsonObject.put("id",88)
        jsonObject.put("nombre","karl")
        jsonObject.put("telefono","22-22-22")

        val request = JsonObjectRequest(
            Request.Method.DELETE,getString(R.string.url)+"eliminarProfesor/"+id,jsonObject,
            { response ->
                // Process the json
                try {
                    // etCord.setText("Response: $response")
                    println(response)
                }catch (e:Exception){
                    //etClima.setText("Exception: $e")
                    println(e)
                }

            }, {
                // Error in request
                //  etHumedad.setText("Volley error: $it")
                println("Error reques: t"+it)
                println("Error request:"+it)
                if(it.message?.contains("false") == true){
                    println("Fall??")

                }
                if(it.message?.contains("true") == true){
                    println("Bien")
                }
            })

        // Volley request policy, only one time request to avoid duplicate transaction
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            // 0 means no retry
            0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
            1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        // Add the volley post request to the request queue
        VolleySingleton.getInstance(activity).addToRequestQueue(request)
    }

}