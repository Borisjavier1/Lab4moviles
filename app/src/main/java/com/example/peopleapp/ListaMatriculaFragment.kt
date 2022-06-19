package com.example.peopleapp

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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.*
import java.util.concurrent.CountDownLatch
import kotlin.collections.ArrayList

class ListaMatriculaFragment : FragmentUtils(){
    private lateinit var appBarConfiguration: AppBarConfiguration
    var matriculas: Matriculas = Matriculas.instance

    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: RecyclerView_Adapter7
    lateinit var matricula: MatriculaAPIItem
    var position: Int = 0
    var client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var estudiante  = arguments?.getInt("estudiante")
        var ciclo = arguments?.getInt("ciclo")

        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_matricula_lista, container, false)

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

                Collections.swap(matriculas.getMatriculasStudentCiclo(estudiante,ciclo), fromPosition, toPosition)

                recyclerViewElement.adapter?.notifyItemMoved(fromPosition, toPosition)

                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                position = viewHolder.adapterPosition

                if (direction == ItemTouchHelper.LEFT) {//Delete
                    var index = getIndex(position)
                    //matriculas.deleteMatricula(index)
                    get(matricula.id_alumno,matricula.id_grupo)
                    recyclerViewElement.adapter?.notifyItemRemoved(position)

                    Snackbar.make(recyclerViewElement, matricula.id.toString() + " desmatriculado/a", Snackbar.LENGTH_LONG).setAction("Undo") {
                        matriculas.getMatriculas().add(position, matricula)
                        recyclerViewElement.adapter?.notifyItemInserted(position)
                    }.show()

                    adaptador = RecyclerView_Adapter7(matriculas.getMatriculasStudentCiclo(estudiante,ciclo))
                    recyclerViewElement.adapter = adaptador
                    changeFragment(MatricularseFragment())

                } else { //Edit
                    var index = getIndex(position)
                    //matriculas.deleteMatricula(index)
                    get(matricula.id_alumno,matricula.id_grupo)
                    recyclerViewElement.adapter?.notifyItemRemoved(position)

                    Snackbar.make(recyclerViewElement, matricula.id.toString() + " desmatriculado/a", Snackbar.LENGTH_LONG).setAction("Undo") {
                        matriculas.getMatriculas().add(position, matricula)
                        recyclerViewElement.adapter?.notifyItemInserted(position)
                    }.show()

                    adaptador = RecyclerView_Adapter7(matriculas.getMatriculasStudentCiclo(estudiante,ciclo))
                    recyclerViewElement.adapter = adaptador
                    changeFragment(MatricularseFragment())
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
                    this@ListaMatriculaFragment.context,
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@ListaMatriculaFragment.context!!, R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            this@ListaMatriculaFragment.context!!,
                            R.color.red
                        )
                    )
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewElement)

        val add: FloatingActionButton = view.findViewById(R.id.add)
        add.setOnClickListener { view ->
            var bundle = Bundle()
            bundle.putSerializable("estudiante", estudiante)
            bundle.putSerializable("ciclo", ciclo)
            var fragment = MatricularEstudianteFragment()
            fragment.arguments = bundle
            changeFragment(fragment)
        }
        return view;
    }
    private fun getListOfPersons() {
        var estudiante  = arguments?.getInt("estudiante")
        var ciclo = arguments?.getInt("ciclo")
        val Nciclos = ArrayList<MatriculaAPIItem>()
        for (p in matriculas.getMatriculasStudentCiclo(estudiante,ciclo)) {
            Nciclos.add(p)
        }
        adaptador = RecyclerView_Adapter7(Nciclos)
        recyclerViewElement.adapter = adaptador
    }
    private fun getIndex(index: Int): Int{
        var estudiante  = arguments?.getInt("estudiante")
        var ciclo = arguments?.getInt("ciclo")

        var index = index
        var adapterItems = adaptador.itemsList
        var listaCiclos = matriculas.getMatriculasStudentCiclo(estudiante,ciclo)

        matricula = adapterItems?.get(index)!!

        index = listaCiclos.indexOfFirst {
            it.id == matricula.id
        }

        return index
    }

    fun get(al:Int,gru:Int) {
        // val etLocation = findViewById<EditText>(R.id.etLocation)
        val request = Request.Builder()
            //.url("http://10.0.2.2:28019/api/usuarios")
            .url(getString(R.string.url)+"desmatricular/"+al+"/"+gru)
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
                countDownLatch.countDown();
                println(valor)
                //Toast.makeText(activity,valor,Toast.LENGTH_SHORT).show()
            }
        })
        countDownLatch.await();
    }
}