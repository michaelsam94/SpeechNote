package com.example.speechnote.data.local

import androidx.room.*
import com.example.speechnote.data.model.Session

@Dao
interface SessionDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: Session): Long


    @Query("SELECT * FROM  Session")
    suspend fun getAllSession(): List<Session>


    @Update
    suspend fun updateSession(session: Session)



}