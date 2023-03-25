package com.interactive.sportz.viewmodel

import androidx.lifecycle.LiveData
import com.interactive.sportz.repository.MatchDetailRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interactive.sportz.models.CricketModel
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MatchDetailsViewModel @Inject constructor(private val repo: MatchDetailRepo) : ViewModel() {

    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }

    init {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            repo.getMatchDetails()
        }
    }

    val mdata: LiveData<CricketModel>
        get() = repo.matchData
}