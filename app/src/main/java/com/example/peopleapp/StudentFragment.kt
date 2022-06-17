package com.example.peopleapp

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
import com.example.models.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.*
import kotlin.collections.ArrayList

class StudentFragment : FragmentUtils(){
    private lateinit var appBarConfiguration: AppBarConfiguration
    var alumnos: Alumnos = Alumnos.instance

    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: RecyclerView_Adapter6
    lateinit var alumno: AlumnoAPIItem
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

                Collections.swap(alumnos.getAlumnos(), fromPosition, toPosition)

                recyclerViewElement.adapter?.notifyItemMoved(fromPosition, toPosition)

                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                position = viewHolder.adapterPosition

                if (direction == ItemTouchHelper.LEFT) {//Delete

                    var index = getIndex(position)
                    alumnos.deleteAlumno(index)
                    recyclerViewElement.adapter?.notifyItemRemoved(position)

                    Snackbar.make(recyclerViewElement, alumno.nombre + " eliminado/a", Snackbar.LENGTH_LONG).setAction("Undo") {
                        alumnos.getAlumnos().add(position, alumno)
                        recyclerViewElement.adapter?.notifyItemInserted(position)
                    }.show()

                    adaptador = RecyclerView_Adapter6(alumnos.getAlumnos())
                    recyclerViewElement.adapter = adaptador

                } else { //Edit
                    alumno = AlumnoAPIItem(
                        alumnos.getAlumnos()[position].carrera,
                        alumnos.getAlumnos()[position].cedula,
                        alumnos.getAlumnos()[position].email,
                        alumnos.getAlumnos()[position].fecha_nac,
                        alumnos.getAlumnos()[position].id,
                        alumnos.getAlumnos()[position].nombre,
                        alumnos.getAlumnos()[position].telefono,

                    )
                    var index = getIndex(position)
                    alumno.position = index;

                    var bundle = Bundle()
                    bundle.putSerializable("alumno", alumno)

                    var editFragment = CreateAlumnoFragment()
                    editFragment.arguments = bundle

                    setToolbarTitle("Editar Alumno")
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
                    this@StudentFragment.context,
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@StudentFragment.context!!, R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            this@StudentFragment.context!!,
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
            changeFragment(CreateAlumnoFragment())
        }
        return view;
    }
    private fun getListOfPersons() {
        val NAlumnos = ArrayList<AlumnoAPIItem>()
        for (p in alumnos.getAlumnos()) {
            NAlumnos.add(p)
        }
        adaptador = RecyclerView_Adapter6(NAlumnos)
        recyclerViewElement.adapter = adaptador
    }
    private fun getIndex(index: Int): Int{
        var index = index
        var adapterItems = adaptador.itemsList
        var listaAlumnos = alumnos.getAlumnos()

        alumno = adapterItems?.get(index)!!

        index = listaAlumnos.indexOfFirst {
            it.nombre == alumno.nombre
        }

        return index
    }
}