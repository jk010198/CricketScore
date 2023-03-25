package com.interactive.sportz.network

import com.interactive.sportz.models.CricketModel
import retrofit2.Response
import retrofit2.http.GET

interface NetworkApi {

    @GET("nzin01312019187360.json")
    //@GET("sapk01222019186652.json")
    suspend fun getMatchDetails(): Response<CricketModel>

}