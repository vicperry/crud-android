package com.cursoandroid.cadastro.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.room.Room
import com.cursoandroid.cadastro.bd.MyDataBase
import com.cursoandroid.cadastro.bd.model.Contato
import com.cursoandroid.cadastro.databinding.ActivityMainBinding
import com.cursoandroid.cadastro.rv.ListaActivity

class MainActivity : AppCompatActivity() {

    lateinit var db : MyDataBase
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db = Room.databaseBuilder(
            applicationContext,
            MyDataBase::class.java,
            "cadastroContato").build()
        binding.cadastrar.setOnClickListener {
            val contato = Contato (0,
                binding.nome.text.toString(),
                binding.endereco.text.toString())

                Thread(Runnable {
                    db.contatoDAO().insertAll(contato)
                    runOnUiThread(Runnable {
                        var texto: String = ""
                        binding.nome.text = createEditable(texto)
                        binding.endereco.text = createEditable(texto)
                    })
                }).start()

            Toast.makeText(this, "Contato salvo.", Toast.LENGTH_LONG).show()
        }

        binding.listar.setOnClickListener{
            var intent = Intent(applicationContext, ListaActivity::class.java)
            startActivity(intent)
        }

    }

    private fun createEditable(texto: String) =
        Editable.Factory.getInstance().newEditable(texto)
}