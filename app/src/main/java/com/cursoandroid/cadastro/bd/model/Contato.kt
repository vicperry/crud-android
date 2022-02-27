package com.cursoandroid.cadastro.bd.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Contato (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "nome")
    var nome: String?,
    @ColumnInfo
    var endereco: String?
)