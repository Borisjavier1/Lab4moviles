package com.example.peopleapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.models.MatriculaAPIItem

class RecyclerView_Adapter7(private var items: ArrayList<MatriculaAPIItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var itemsList: ArrayList<MatriculaAPIItem>? = null

    lateinit var mcontext: Context

    class PersonHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        this.itemsList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val personListView = LayoutInflater.from(parent.context).inflate(R.layout.templatecursos, parent, false)
        val sch = PersonHolder(personListView)
        mcontext = parent.context
        return sch
    }

    override fun getItemCount(): Int {
        return itemsList?.size!!
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = itemsList?.get(position)

        holder.itemView.findViewById<TextView>(R.id.tvNombre)?.text = "Id de grupo: "+item?.id_grupo + " Id est: "+item?.id_alumno
        //holder.itemView.findViewById<ImageView>(R.id.ivFoto).setImageResource(item?.foto!!)

    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }


    /*override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    itemsList = items
                } else {
                    val resultList = ArrayList<Matricula>()
                    for (row in items) {
                        if (row.cedEstudiante.toLowerCase().contains(charSearch.toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    itemsList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = itemsList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemsList = results?.values as ArrayList<MatriculaAPIItem>
                notifyDataSetChanged()
            }

        }
    }*/


}

