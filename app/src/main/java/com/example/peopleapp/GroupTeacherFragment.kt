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

class GroupTeacherFragment : FragmentUtils(){
    private lateinit var appBarConfiguration: AppBarConfiguration

    var grupos: Grupos = Grupos.instance

    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: RecyclerView_Adapter4
    lateinit var grupo: GrupoAPIItem
    var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sp: SharedPreferences
        var myContext = activity!!
        sp = myContext.getSharedPreferences("Session Data", Context.MODE_PRIVATE)
        var ced = sp.getString("cedula", "")

        if(grupos.getGruposProfesor(ced)[0].numero!=0) {

            // Inflate the layout for this fragment
            var view = inflater.inflate(R.layout.fragment_group_teacher, container, false)

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

                    Collections.swap(grupos.getGruposProfesor(ced), fromPosition, toPosition)

                    recyclerViewElement.adapter?.notifyItemMoved(fromPosition, toPosition)

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    position = viewHolder.adapterPosition

                    if (direction == ItemTouchHelper.RIGHT) {//Delete

                        grupo = GrupoAPIItem(
                            grupos.getGrupos()[position].ciclo,
                            grupos.getGrupos()[position].curso,
                            grupos.getGrupos()[position].numero,
                            grupos.getGrupos()[position].id,
                            grupos.getGrupos()[position].horario,
                            grupos.getGrupos()[position].profesor
                        )
                        var index = getIndex(position)
                        grupo.position = index;

                        var bundle = Bundle()
                        bundle.putInt("grupo", grupo.id)

                        var editFragment = EstudianteNotaFragment()
                        editFragment.arguments = bundle

                        setToolbarTitle("Editar Matricula")
                        changeFragment(fragmentUtils = editFragment)

                    } else {
                        grupo = GrupoAPIItem(
                            grupos.getGrupos()[position].ciclo,
                            grupos.getGrupos()[position].curso,
                            grupos.getGrupos()[position].numero,
                            grupos.getGrupos()[position].id,
                            grupos.getGrupos()[position].horario,
                            grupos.getGrupos()[position].profesor
                        )
                        var index = getIndex(position)
                        grupo.position = index;

                        var bundle = Bundle()
                        bundle.putInt("grupo", grupo.id)

                        var editFragment = EstudianteNotaFragment()
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
                        this@GroupTeacherFragment.context,
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
                                this@GroupTeacherFragment.context!!,
                                R.color.green
                            )
                        )
                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_edit_24)
                        .addSwipeRightBackgroundColor(
                            ContextCompat.getColor(
                                this@GroupTeacherFragment.context!!,
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
            changeFragment(GroupTeacherFragment())
        }*/
            return view
        }else {
            Toast.makeText(activity,"El profesor no tiene grupos asignados",Toast.LENGTH_SHORT).show()
            return view;
        }
    }
    private fun getListOfPersons() {
        val sp: SharedPreferences
        var myContext = activity!!
        sp = myContext.getSharedPreferences("Session Data", Context.MODE_PRIVATE)
        var ced = sp.getString("cedula", "")
        val Ngrupos = ArrayList<GrupoAPIItem>()
        for (p in grupos.getGruposProfesor(ced)) {
            Ngrupos.add(p)
        }
        adaptador = RecyclerView_Adapter4(Ngrupos)
        recyclerViewElement.adapter = adaptador
    }
    private fun getIndex(index: Int): Int{
        val sp: SharedPreferences
        var myContext = activity!!
        sp = myContext.getSharedPreferences("Session Data", Context.MODE_PRIVATE)
        var ced = sp.getString("cedula", "")
        var index = index
        var adapterItems = adaptador.itemsList
        var listaGrupos = grupos.getGruposProfesor(ced)

        grupo = adapterItems?.get(index)!!

        index = listaGrupos.indexOfFirst {
            it.id == grupo.id
        }

        return index
    }
}