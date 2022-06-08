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

class MatriculaEstudianteFragment : FragmentUtils(){
    private lateinit var appBarConfiguration: AppBarConfiguration
    var matriculas: Matriculas = Matriculas.instance

    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: RecyclerView_Adapter7
    lateinit var matricula: Matricula
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

                Collections.swap(matriculas.getMatriculasStudent(ced), fromPosition, toPosition)

                recyclerViewElement.adapter?.notifyItemMoved(fromPosition, toPosition)

                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                position = viewHolder.adapterPosition

                if (direction == ItemTouchHelper.LEFT) {//Delete

                    matricula = Matricula(
                        matriculas.getMatriculasStudent(ced)[position].codGrupo,
                        matriculas.getMatriculasStudent(ced)[position].cedEstudiante,
                        matriculas.getMatriculasStudent(ced)[position].nota,
                        matriculas.getMatriculasStudent(ced)[position].estado

                    )
                    var index = getIndex(position)
                    matricula.position = index;

                    var bundle = Bundle()
                    bundle.putSerializable("matricula", matricula)

                    var editFragment = VerMatriculaFragment()
                    editFragment.arguments = bundle

                    setToolbarTitle("Ver Matricula")
                    changeFragment(fragmentUtils = editFragment)

                } else { //Edit
                    matricula = Matricula(
                        matriculas.getMatriculasStudent(ced)[position].codGrupo,
                        matriculas.getMatriculasStudent(ced)[position].cedEstudiante,
                        matriculas.getMatriculasStudent(ced)[position].nota,
                        matriculas.getMatriculasStudent(ced)[position].estado

                    )
                    var index = getIndex(position)
                    matricula.position = index;

                    var bundle = Bundle()
                    bundle.putSerializable("matricula", matricula)

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
                    this@MatriculaEstudianteFragment.context,
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@MatriculaEstudianteFragment.context!!, R.color.green))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            this@MatriculaEstudianteFragment.context!!,
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
        val sp: SharedPreferences
        var myContext = activity!!
        sp = myContext.getSharedPreferences("Session Data", Context.MODE_PRIVATE)
        var ced = sp.getString("cedula", "")
        val Nciclos = ArrayList<Matricula>()
        for (p in matriculas.getMatriculasStudent(ced)) {
            Nciclos.add(p)
        }
        adaptador = RecyclerView_Adapter7(Nciclos)
        recyclerViewElement.adapter = adaptador
    }
    private fun getIndex(index: Int): Int{
        val sp: SharedPreferences
        var myContext = activity!!
        sp = myContext.getSharedPreferences("Session Data", Context.MODE_PRIVATE)
        var ced = sp.getString("cedula", "")
        var index = index
        var adapterItems = adaptador.itemsList
        var listaCiclos = matriculas.getMatriculasStudent(ced)

        matricula = adapterItems?.get(index)!!

        index = listaCiclos.indexOfFirst {
            it.cedEstudiante == matricula.cedEstudiante
        }

        return index
    }
}