package com.todayquest.common

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data-store-name")

@RequiresApi(Build.VERSION_CODES.O)
object CommonUtils {
    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(
            LocalDateTime::class.java,
            JsonDeserializer { json, _, _ ->
                LocalDateTime.parse(json.asString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
            }
        )
        .registerTypeAdapter(
            LocalDate::class.java,
            JsonDeserializer { json, _, _ ->
                LocalDate.parse(json.asString, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            })
        .registerTypeAdapter(
            LocalTime::class.java,
            JsonDeserializer { json, _, _ ->
                LocalTime.parse(json.asString, DateTimeFormatter.ofPattern("HH:mm:ss"))
            })
        .create()

}