package com.example.speechnote.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speechnote.data.SessionRepository
import com.example.speechnote.data.model.Session
import kotlinx.coroutines.launch

class DetailsViewModel(private val sessionRepository: SessionRepository) : ViewModel() {


    fun updateSessionContent(session: Session) {
        viewModelScope.launch {
            sessionRepository.updateSession(session)
        }
    }


}