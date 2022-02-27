package com.cursoandroid.cadastro.rv

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.cursoandroid.cadastro.R
import com.cursoandroid.cadastro.bd.model.Contato

class ContatoAdapter(
    var context: Context,
    var itens: ArrayList<Contato>
): RecyclerView.Adapter<ContatoViewHolder>() {
    lateinit var contato: Contato
    var selectedPosition: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lista_item, parent, false)
        val holder = ContatoViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(viewHolder: ContatoViewHolder, position: Int) {
        contato = itens.get(viewHolder.adapterPosition)
        viewHolder.nome.text = contato.nome
        viewHolder.endereco.text = contato.endereco

        viewHolder.itemView.setOnClickListener {
            Toast.makeText(context, "Contato Clicado: " + contato.nome, Toast.LENGTH_LONG).show()
            contato = itens.get(viewHolder.adapterPosition)
            selectedPosition = viewHolder.adapterPosition
        }
        viewHolder.itemView.setOnCreateContextMenuListener(object : View.OnCreateContextMenuListener{
            override fun onCreateContextMenu(
                menu: ContextMenu?,
                v: View?,
                menuInfo: ContextMenu.ContextMenuInfo?
            ) {
                menu!!.setHeaderTitle("Ações: ")
                menu!!.add(0, 0, 0, "Edit")
                menu!!.add(0, 1, 1, "Delete")

                contato = itens.get(viewHolder.adapterPosition)
                selectedPosition = viewHolder.adapterPosition
            }
        })

    }

    override fun getItemCount(): Int {
        return itens.size
    }

    fun deleteContato() {
        itens.remove(contato)
        this.notifyItemRemoved(selectedPosition)
    }
}