package com.example.speechnote.ui

import SingleLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speechnote.data.SessionRepository
import com.example.speechnote.data.model.Session
import com.example.speechnote.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SessionViewModel(private val sessionRepository: SessionRepository) : ViewModel() {

    private var sessions = MutableLiveData<Resource<List<Session>>>()
    private var lastInsertedSessions = SingleLiveEvent<Resource<Session>>()

    init {
        getSessions()
    }

    fun getSessions() {
        sessions.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val result = sessionRepository.getSessions()
            sessions.postValue(Resource.Success(result))
        }
    }

    fun addSession(session: Session) {
        viewModelScope.launch {
            sessionRepository.addSession(session)
            val allSessions = sessionRepository.getSessions()
            sessions.postValue(Resource.Success(allSessions))
            lastInsertedSessions.postValue(Resource.Success(allSessions.last()))
        }
    }

    fun getSessionsLiveData() = sessions as LiveData<Resource<List<Session>>>
    fun getLastInsertedSessionLiveData() = lastInsertedSessions

}