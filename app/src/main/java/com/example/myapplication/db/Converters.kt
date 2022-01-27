package com.example.myapplication.db

import androidx.room.TypeConverter
import com.example.myapplication.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }


    @TypeConverter
    fun toSource(name: String): Source {
        return Source(id = name, name = name)
    }
}