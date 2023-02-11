package com.todayquest.common

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.todayquest.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object GoogleSignInModule {


    @Provides
    fun provideGoogleSignInClient(@ApplicationContext context: Context, gso: GoogleSignInOptions): GoogleSignInClient {
        return GoogleSignIn.getClient(context, gso)
    }

    @Provides
    fun provideGoogleSignInOptions(@ApplicationContext context: Context): GoogleSignInOptions {
        val serverClientId = context.getString(R.string.server_client_id)
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(serverClientId)
            .build()
    }



}