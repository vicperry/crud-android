package com.cursoandroid.cadastro.bd.dao

import androidx.room.*
import com.cursoandroid.cadastro.bd.model.Contato

@Dao
interface ContatoDao {
    @Query("select * from contato")
    fun getAll(): List<Contato>

    @Query("select * from contato where id in (:userIds)")
    fun loadAllByIds(userIds: IntArray) : List<Contato>

    @Query("select * from contato where nome like :nome")
    fun findByName(nome: String): Contato

    @Insert
    fun insertAll(vararg users: Contato)

    @Delete
    fun delete(user: Contato)

    @Update
    fun update(user: Contato)

}