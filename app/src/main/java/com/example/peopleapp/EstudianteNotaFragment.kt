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
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.*
import kotlin.collections.ArrayList

class EstudianteNotaFragment : FragmentUtils(){
    private lateinit var appBarConfiguration: AppBarConfiguration
    var notas: EstudiantesNotas = EstudiantesNotas.instance

    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: RecyclerView_Adapter10
    lateinit var estudianteNota: EstudianteNota
    var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var grupo  = arguments?.getInt("grupo")


            // Inflate the layout for this fragment
            var view = inflater.inflate(R.layout.fragment_matricula, container, false)

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

                    Collections.swap(notas.getMatriculas(grupo), fromPosition, toPosition)

                    recyclerViewElement.adapter?.notifyItemMoved(fromPosition, toPosition)

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    position = viewHolder.adapterPosition

                    if (direction == ItemTouchHelper.LEFT) {//Delete
                        estudianteNota = EstudianteNota(
                            notas.getMatriculas(grupo)[position].cedula,
                            notas.getMatriculas(grupo)[position].grupo,
                            notas.getMatriculas(grupo)[position].id_alumno,
                            notas.getMatriculas(grupo)[position].id_historial,
                            notas.getMatriculas(grupo)[position].nombre,
                            notas.getMatriculas(grupo)[position].nota

                        )
                        var index = getIndex(position)
                        estudianteNota.position = index;

                        var bundle = Bundle()
                        bundle.putSerializable("estudianteNota", estudianteNota)

                        var editFragment = CreateMatriculaFragment()
                        editFragment.arguments = bundle

                        setToolbarTitle("Editar Nota")
                        changeFragment(fragmentUtils = editFragment)

                    } else { //Edit
                        estudianteNota = EstudianteNota(
                            notas.getMatriculas(grupo)[position].cedula,
                            notas.getMatriculas(grupo)[position].grupo,
                            notas.getMatriculas(grupo)[position].id_alumno,
                            notas.getMatriculas(grupo)[position].id_historial,
                            notas.getMatriculas(grupo)[position].nombre,
                            notas.getMatriculas(grupo)[position].nota

                        )
                        var index = getIndex(position)
                        estudianteNota.position = index;

                        var bundle = Bundle()
                        bundle.putSerializable("estudianteNota", estudianteNota)

                        var editFragment = CreateMatriculaFragment()
                        editFragment.arguments = bundle

                        setToolbarTitle("Editar Nota")
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
                        this@EstudianteNotaFragment.context,
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                        .addSwipeLeftBackgroundColor(
                            ContextCompat.getColor(
                                this@EstudianteNotaFragment.context!!,
                                R.color.green
                            )
                        )
                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_edit_24)
                        .addSwipeRightBackgroundColor(
                            ContextCompat.getColor(
                                this@EstudianteNotaFragment.context!!,
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

    }
    private fun getListOfPersons() {
        var grupo  = arguments?.getInt("grupo")
        val Nciclos = ArrayList<EstudianteNota>()
        for (p in notas.getMatriculas(grupo)) {
            Nciclos.add(p)
        }
        adaptador = RecyclerView_Adapter10(Nciclos)
        recyclerViewElement.adapter = adaptador
    }
    private fun getIndex(index: Int): Int{
        var grupo  = arguments?.getInt("grupo")

        var index = index
        var adapterItems = adaptador.itemsList
        var listaCiclos = notas.getMatriculas(grupo)

        estudianteNota = adapterItems?.get(index)!!

        index = listaCiclos.indexOfFirst {
            it.id_historial == estudianteNota.id_historial
        }

        return index
    }


}