
package com.example.planifyeventos.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object  RetrofitEmailFactory {
    fun getEmailService(): EmailService {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmailService::class.java)
    }
}
