package com.example.speechnote.data

import com.example.speechnote.data.local.OfflineDataSource
import com.example.speechnote.data.model.Session

class SessionRepository(
    private val offlineDataSource: OfflineDataSource,
) {



    suspend fun getSessions() : List<Session> {
        return offlineDataSource.getSessions()
    }

    suspend fun addSession(session: Session) {
        return offlineDataSource.insertSession(session)
    }

    suspend fun updateSession(session: Session) {
        offlineDataSource.updateSession(session)
    }

}