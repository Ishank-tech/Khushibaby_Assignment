package com.example.tmdb.utils

import androidx.room.TypeConverter
import com.example.tmdb.data.models.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenreConverter {

    @TypeConverter
    fun fromGenreToString(genre:List<Genre>) : String{
        return Gson().toJson(genre)
    }

    @TypeConverter
    fun fromStringToGenre(value:String) : List<Genre>{
        return Gson().fromJson(value, object : TypeToken<List<Genre>>() {}.type)
    }
}