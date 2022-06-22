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
import com.example.models.CursoAPIItem
import com.example.models.Cursos
import com.example.models.VolleySingleton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONObject
import java.util.*


class CourseFragment : FragmentUtils(){
    private lateinit var appBarConfiguration: AppBarConfiguration
    var cursos: Cursos = Cursos.instance

    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: RecyclerView_Adapter3
    lateinit var curso: CursoAPIItem
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

                Collections.swap(cursos.getCursos(), fromPosition, toPosition)

                recyclerViewElement.adapter?.notifyItemMoved(fromPosition, toPosition)

                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                position = viewHolder.adapterPosition

                if (direction == ItemTouchHelper.LEFT) {//Delete

                    var index = getIndex(position)
                    //cursos.deleteCurso(index)
                    delete(index)
                    recyclerViewElement.adapter?.notifyItemRemoved(position)

                    Snackbar.make(recyclerViewElement, curso.nombre + " eliminado/a", Snackbar.LENGTH_LONG).setAction("Undo") {
                        cursos.getCursos().add(position, curso)
                        recyclerViewElement.adapter?.notifyItemInserted(position)
                    }.show()

                    adaptador = RecyclerView_Adapter3(cursos.getCursos())
                    recyclerViewElement.adapter = adaptador
                    changeFragment(CourseFragment())

                } else { //Edit
                    curso = CursoAPIItem(

                        cursos.getCursos()[position].anio_ciclo,
                        cursos.getCursos()[position].carrera,
                        cursos.getCursos()[position].ciclo,
                        cursos.getCursos()[position].codigo,
                        cursos.getCursos()[position].creditos,
                        cursos.getCursos()[position].horas_sem,
                        cursos.getCursos()[position].id,
                        cursos.getCursos()[position].nombre,
                        cursos.getCursos()[position].nombre_carrera,
                        cursos.getCursos()[position].numero_ciclo

                    )
                    var index = getIndex(position)
                    curso.position = index;

                    var bundle = Bundle()
                    bundle.putSerializable("curso", curso)

                    var editFragment = CreateCourseFragment()
                    editFragment.arguments = bundle

                    setToolbarTitle("Editar Curso")
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
                    this@CourseFragment.context,
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@CourseFragment.context!!, R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            this@CourseFragment.context!!,
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
            changeFragment(CreateCourseFragment())
        }
        return view;
    }
    private fun getListOfPersons() {
        val Ncursos = ArrayList<CursoAPIItem>()
        for (p in cursos.getCursos()) {
            Ncursos.add(p)
        }
        adaptador = RecyclerView_Adapter3(Ncursos)
        recyclerViewElement.adapter = adaptador
    }
    private fun getIndex(index: Int): Int{
        var index = index
        var adapterItems = adaptador.itemsList
        var listaCursos = cursos.getCursos()

        curso = adapterItems?.get(index)!!

        index = listaCursos.indexOfFirst {
            it.nombre == curso.nombre
        }

        return curso.id
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
            Request.Method.DELETE,getString(R.string.url)+"eliminarCurso/"+id,jsonObject,
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
                    println("Falló")
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