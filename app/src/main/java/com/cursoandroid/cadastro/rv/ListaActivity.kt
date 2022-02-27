package com.cursoandroid.cadastro.rv

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.cursoandroid.cadastro.R
import com.cursoandroid.cadastro.bd.MyDataBase
import com.cursoandroid.cadastro.bd.model.Contato
import com.cursoandroid.cadastro.databinding.ListaBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListaActivity : AppCompatActivity() {
    lateinit var db : MyDataBase
    private lateinit var bindingListagem: ListaBinding
    lateinit var itens : ArrayList<Contato>
    lateinit var nome : EditText
    lateinit var endereco : EditText
    lateinit var cadastrar : Button
    lateinit var cancelar : Button
    lateinit var contato : Contato

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingListagem = ListaBinding.inflate(layoutInflater)
        val view = bindingListagem.root
        setContentView(view)


        db = Room.databaseBuilder(
            applicationContext,
            MyDataBase::class.java,
            "cadastroContato").build()
        consulta()

        Toast.makeText(this, "Segure o item para editar ou deletar.", Toast.LENGTH_LONG).show()
    }


    fun consulta() {
        Thread(Runnable {
            itens = db.contatoDAO().getAll() as ArrayList<Contato>
            popular()
        }).start()
    }
    fun popular() {
        runOnUiThread{
            val adapter = ContatoAdapter(applicationContext, itens)
            bindingListagem.recycler.layoutManager = LinearLayoutManager(applicationContext)
            bindingListagem.recycler.itemAnimator = DefaultItemAnimator()
            bindingListagem.recycler.adapter = adapter
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var adapter = bindingListagem.recycler.adapter as ContatoAdapter
        contato = adapter.contato
        if(item!!.itemId == 0) {
            displayDialog()
        } else if ( item!!.itemId == 1) {
            adapter.deleteContato()
            deletarContato(contato)
        }
        return super.onContextItemSelected(item)
    }

    private fun deletarContato(contato: Contato) {
        val timer = Timer()
        val snackbar = Snackbar.make(bindingListagem.recycler, "Contato " + contato.nome + " excluído com sucesso.", Snackbar.LENGTH_LONG)
        snackbar.setAction("Desfazer", View.OnClickListener {
            timer.cancel()
            consulta()
        }).show()
        timer.schedule(object : TimerTask() {
            override fun run() {
                db.contatoDAO().delete(contato)
            }
        }, 5000)
    }

    private fun displayDialog() {
        val dialog = Dialog(this)
        dialog.setTitle("Cadastro")
        dialog.setContentView(R.layout.edit_cadastro)
        dialog.setCancelable(true)
        nome = dialog.findViewById(R.id.nome) as EditText
        endereco = dialog.findViewById(R.id.endereco) as EditText
        cadastrar = dialog.findViewById(R.id.cadastrar) as Button
        cancelar = dialog.findViewById(R.id.cancelar) as Button

        nome.setText(contato.nome)
        endereco.setText(contato.endereco)


        cadastrar.setOnClickListener(View.OnClickListener {
            contato.nome = nome.text.toString()
            contato.endereco = endereco.text.toString()

            atualizarContato(contato)
            dialog.dismiss()
        })
        cancelar.setOnClickListener{
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun atualizarContato(contato: Contato) {
        Thread(Runnable {
            db.contatoDAO().update(contato)
            consulta()
        }).start()
    }
}