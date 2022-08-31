package com.linkjam06.musicapp

import android.app.Application
import android.content.Context
import com.linkjam06.data.apiservice.ApiService
import com.linkjam06.data.dao.MusicRoomDataBase
import com.linkjam06.data.mappers.MusicListMapper
import com.linkjam06.data.repositories.MusicListDatabaseRepo
import com.linkjam06.data.repositories.MusicListRepoImpl
import com.linkjam06.domain.dao.MusicListDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MusicApplication : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    val applicationScope = CoroutineScope(SupervisorJob())

    val musicRepo by lazy { MusicListRepoImpl(ApiService(), MusicListMapper()) }

    val database by lazy { MusicRoomDataBase.getDatabase(this, applicationScope) }
    val musicListDatabaseRepo by lazy { MusicListDatabaseRepo(database.musicListDao()) }


}