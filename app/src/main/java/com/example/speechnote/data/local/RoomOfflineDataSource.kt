package com.example.speechnote.data.local

import com.example.speechnote.data.model.Session

class RoomOfflineDataSource(private val sessionDao: SessionDao) : OfflineDataSource {

    override suspend fun getSessions(): List<Session> = sessionDao.getAllSession()

    override suspend fun insertSession(session: Session) {
        sessionDao.insertSession(session)
    }

    override suspend fun updateSession(session: Session) {
        sessionDao.updateSession(session)
    }

}