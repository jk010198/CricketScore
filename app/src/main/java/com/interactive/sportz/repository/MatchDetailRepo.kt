package com.interactive.sportz.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.interactive.sportz.models.CricketModel
import com.interactive.sportz.network.NetworkApi
import retrofit2.Response
import javax.inject.Inject

class MatchDetailRepo @Inject constructor(private val networkApi: NetworkApi) {

    private val usersLiveData = MutableLiveData<CricketModel>()

    val matchData: LiveData<CricketModel>
        get() = usersLiveData

    suspend fun getMatchDetails(){
        val result = networkApi.getMatchDetails()
        if (result.isSuccessful && result.body() != null){
            usersLiveData.postValue(result.body())
        }
    }
}