package com.todayquest.common

import android.os.Build
import androidx.annotation.RequiresApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface CommonService {

    companion object {
        val services = mutableMapOf<Class<out CommonService>, CommonService>()

        @RequiresApi(Build.VERSION_CODES.O)
        inline fun <reified T : CommonService> getInstance(): T {
            val clazz = T::class.java

            return services.getOrPut(clazz) {
                Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080")
                    .addConverterFactory(GsonConverterFactory.create(CommonUtils.gson))
                    .build()
                    .create(clazz)
            } as T
        }

    }
}