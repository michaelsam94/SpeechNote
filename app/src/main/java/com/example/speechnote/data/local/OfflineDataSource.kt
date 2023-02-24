package com.example.speechnote.data.local

import com.example.speechnote.data.model.Session

interface OfflineDataSource {

    suspend fun getSessions(): List<Session> = emptyList()

    suspend fun insertSession(session: Session)

    suspend fun updateSession(session: Session)

}