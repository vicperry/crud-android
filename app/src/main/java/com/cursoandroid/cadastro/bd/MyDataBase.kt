package com.cursoandroid.cadastro.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cursoandroid.cadastro.bd.dao.ContatoDao
import com.cursoandroid.cadastro.bd.model.Contato

@Database(entities = arrayOf(Contato::class), version = 1, exportSchema = false)
abstract class MyDataBase: RoomDatabase() {

    abstract fun contatoDAO(): ContatoDao

}