package com.example.peopleapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.models.*
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.*
import kotlin.collections.ArrayList

class HistorialEstudiantesFragments : FragmentUtils(){
    private lateinit var appBarConfiguration: AppBarConfiguration
    var historiales: Historiales = Historiales.instance

    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: RecyclerView_Adapter11
    lateinit var historial: HistorialAlumno
    var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setToolbarTitle("Historial de matriculas")
        val sp: SharedPreferences
        var myContext = activity!!
        sp = myContext.getSharedPreferences("Session Data", Context.MODE_PRIVATE)
        var ced = sp.getString("cedula", "")

        if(historiales.getHistoriales(ced)[0].numero_grupo!="0") {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_matricula_estudiante, container, false)

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

                Collections.swap(historiales.getHistoriales(ced), fromPosition, toPosition)

                recyclerViewElement.adapter?.notifyItemMoved(fromPosition, toPosition)

                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                position = viewHolder.adapterPosition

                if (direction == ItemTouchHelper.LEFT) {//Delete

                    historial = HistorialAlumno(
                        historiales.getHistoriales(ced)[position].anio_ciclo,
                        historiales.getHistoriales(ced)[position].ciclo,
                        historiales.getHistoriales(ced)[position].curso,
                        historiales.getHistoriales(ced)[position].estado,
                        historiales.getHistoriales(ced)[position].grupo,
                        historiales.getHistoriales(ced)[position].id,
                        historiales.getHistoriales(ced)[position].nombre_curso,
                        historiales.getHistoriales(ced)[position].nota,
                        historiales.getHistoriales(ced)[position].numero_ciclo,
                        historiales.getHistoriales(ced)[position].numero_grupo

                    )
                    var index = getIndex(position)
                    historial.position = index;

                    var bundle = Bundle()
                    bundle.putSerializable("historial", historial)
                    bundle.putString("cedula", ced)

                    var editFragment = VerMatriculaFragment()
                    editFragment.arguments = bundle

                    setToolbarTitle("Ver historial")
                    changeFragment(fragmentUtils = editFragment)

                } else { //Edit
                    historial = HistorialAlumno(
                        historiales.getHistoriales(ced)[position].anio_ciclo,
                        historiales.getHistoriales(ced)[position].ciclo,
                        historiales.getHistoriales(ced)[position].curso,
                        historiales.getHistoriales(ced)[position].estado,
                        historiales.getHistoriales(ced)[position].grupo,
                        historiales.getHistoriales(ced)[position].id,
                        historiales.getHistoriales(ced)[position].nombre_curso,
                        historiales.getHistoriales(ced)[position].nota,
                        historiales.getHistoriales(ced)[position].numero_ciclo,
                        historiales.getHistoriales(ced)[position].numero_grupo

                    )
                    var index = getIndex(position)
                    historial.position = index;

                    var bundle = Bundle()
                    bundle.putSerializable("historial", historial)
                    bundle.putString("cedula", ced)

                    var editFragment = VerMatriculaFragment()
                    editFragment.arguments = bundle

                    setToolbarTitle("Editar Matricula")
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
                    this@HistorialEstudiantesFragments.context,
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@HistorialEstudiantesFragments.context!!, R.color.green))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            this@HistorialEstudiantesFragments.context!!,
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

        /*val add: FloatingActionButton = view.findViewById(R.id.add)
        add.setOnClickListener { view ->
            changeFragment(CreateCycleFragment())
        }*/
        return view;
        }else {
            Toast.makeText(activity,"El alumno no ha matriculado cursos", Toast.LENGTH_SHORT).show()
            return view;
        }
    }
    private fun getListOfPersons() {
        val sp: SharedPreferences
        var myContext = activity!!
        sp = myContext.getSharedPreferences("Session Data", Context.MODE_PRIVATE)
        var ced = sp.getString("cedula", "")
        val Nciclos = ArrayList<HistorialAlumno>()
        for (p in historiales.getHistoriales(ced)) {
            Nciclos.add(p)
        }
        adaptador = RecyclerView_Adapter11(Nciclos)
        recyclerViewElement.adapter = adaptador
    }
    private fun getIndex(index: Int): Int{
        val sp: SharedPreferences
        var myContext = activity!!
        sp = myContext.getSharedPreferences("Session Data", Context.MODE_PRIVATE)
        var ced = sp.getString("cedula", "")
        var index = index
        var adapterItems = adaptador.itemsList
        var listaCiclos = historiales.getHistoriales(ced)

        historial = adapterItems?.get(index)!!

        index = listaCiclos.indexOfFirst {
            it.id == historial.id
        }

        return index
    }
}