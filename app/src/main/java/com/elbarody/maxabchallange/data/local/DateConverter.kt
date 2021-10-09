package com.elbarody.maxabchallange.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


object DateConverter {
    var gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<String> {
        if (data == null) {
            return emptyList()
        }
        val listType: Type = object : TypeToken<List<String?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<String?>?): String = gson.toJson(someObjects)
}