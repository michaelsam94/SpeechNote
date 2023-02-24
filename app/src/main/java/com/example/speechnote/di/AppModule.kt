package com.example.speechnote.di

import androidx.room.Room
import com.example.speechnote.data.SessionRepository
import com.example.speechnote.data.local.OfflineDataSource
import com.example.speechnote.data.local.RoomOfflineDataSource
import com.example.speechnote.data.local.SessionDatabase
import com.example.speechnote.ui.DetailsViewModel
import com.example.speechnote.ui.SessionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val roomModule = module {
    single {
        Room.databaseBuilder(get(), SessionDatabase::class.java, "SESSIONS_DB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<SessionDatabase>().getSessionDao() }
}


val repoModule = module {
    single { SessionRepository(get()) }

    factory  <OfflineDataSource>{ RoomOfflineDataSource(get()) }

}

val viewModelModule = module {
    viewModel { SessionViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
}