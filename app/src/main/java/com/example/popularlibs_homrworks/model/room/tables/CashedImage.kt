package com.example.popularlibs_homrworks.model.room.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

//класс таблицы для соответствия url для аватарок и пути к файлу на диске
@Entity
data class CashedImage(
    @PrimaryKey var url: String,
    var localPath: String)