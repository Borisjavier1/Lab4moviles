package com.example.peopleapp

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.models.Curso
import com.example.models.Cursos
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.*


class CourseOfertaFragment : FragmentUtils(){
    private lateinit var appBarConfiguration: AppBarConfiguration
    var cursos: Cursos = Cursos.instance

    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: RecyclerView_Adapter3
    lateinit var curso: Curso
    var position: Int = 0





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val datosRecuperados = arguments
        val carrera = datosRecuperados?.getString("carrera")
        val ciclo = datosRecuperados?.getString("ciclo")
        //Toast.makeText(activity,carrera+ciclo,Toast.LENGTH_SHORT).show();

        var view = inflater.inflate(R.layout.fragment_course_oferta, container, false)

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

        view.findViewById<Button>(R.id.back).setOnClickListener {
            changeFragment(OfertaFragment())
        }
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

                Collections.swap(cursos.getCursosCicloCarrera(carrera,ciclo), fromPosition, toPosition)

                recyclerViewElement.adapter?.notifyItemMoved(fromPosition, toPosition)

                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                position = viewHolder.adapterPosition

                if (direction == ItemTouchHelper.LEFT) {//Delete

                    curso = Curso(
                        cursos.getCursos()[position].codigo,
                        cursos.getCursos()[position].nombre,
                        cursos.getCursos()[position].creditos,
                        cursos.getCursos()[position].horas,
                        cursos.getCursos()[position].carreraCodigo,
                        cursos.getCursos()[position].cicloCodigo

                    )
                    var index = getIndex(position)
                    curso.position = index;

                    var bundle = Bundle()
                    bundle.putSerializable("curso", curso)

                    var editFragment = GroupFragment()
                    editFragment.arguments = bundle

                    setToolbarTitle("Ver grupos")
                    changeFragment(fragmentUtils = editFragment)

                } else { //Edit
                    curso = Curso(
                        cursos.getCursos()[position].codigo,
                        cursos.getCursos()[position].nombre,
                        cursos.getCursos()[position].creditos,
                        cursos.getCursos()[position].horas,
                        cursos.getCursos()[position].carreraCodigo,
                        cursos.getCursos()[position].cicloCodigo

                    )
                    var index = getIndex(position)
                    curso.position = index;

                    var bundle = Bundle()
                    bundle.putSerializable("curso", curso)

                    var editFragment = GroupFragment()
                    editFragment.arguments = bundle

                    setToolbarTitle("Ver grupos")
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
                    this@CourseOfertaFragment.context,
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@CourseOfertaFragment.context!!, R.color.green))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_edit_24)
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            this@CourseOfertaFragment.context!!,
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


        return view;
    }
    private fun getListOfPersons() {
        val datosRecuperados = arguments
        val carrera = datosRecuperados?.getString("carrera")
        val ciclo = datosRecuperados?.getString("ciclo")
        val Ncursos = ArrayList<Curso>()
        for (p in cursos.getCursosCicloCarrera(carrera,ciclo)) {
            Ncursos.add(p)
        }
        adaptador = RecyclerView_Adapter3(Ncursos)
        recyclerViewElement.adapter = adaptador
    }
    private fun getIndex(index: Int): Int{
        val datosRecuperados = arguments
        val carrera = datosRecuperados?.getString("carrera")
        val ciclo = datosRecuperados?.getString("ciclo")
        var index = index
        var adapterItems = adaptador.itemsList
        var listaCursos = cursos.getCursosCicloCarrera(carrera,ciclo)

        curso = adapterItems?.get(index)!!

        index = listaCursos.indexOfFirst {
            it.nombre == curso.nombre
        }

        return index
    }
}