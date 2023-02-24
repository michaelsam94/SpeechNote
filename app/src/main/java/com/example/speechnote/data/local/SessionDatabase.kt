package com.example.speechnote.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.speechnote.data.model.Session

@Database(entities = [Session::class], version = 1 , exportSchema = false)
abstract class SessionDatabase: RoomDatabase() {
    abstract fun getSessionDao(): SessionDao
}