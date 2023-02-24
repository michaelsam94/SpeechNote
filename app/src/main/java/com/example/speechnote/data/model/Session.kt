package com.example.speechnote.data.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Session(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int = 0,
    val title: String,
    val content: String,
): Serializable

