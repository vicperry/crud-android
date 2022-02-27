package com.cursoandroid.cadastro.rv

import android.view.ContextMenu
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cursoandroid.cadastro.R

class ContatoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
    View.OnLongClickListener,
    View.OnClickListener,
    View.OnCreateContextMenuListener{


    val nome : TextView = itemView!!.findViewById(R.id.nome)
    val endereco : TextView = itemView!!.findViewById(R.id.endereco)

    override fun onLongClick(v: View?): Boolean {
        return true
    }

    override fun onClick(v: View?) {
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu!!.setHeaderTitle("Ações: ")
        menu!!.add(0, 0, 0, "Edit")
        menu!!.add(0, 1, 1, "Delete")
    }
}