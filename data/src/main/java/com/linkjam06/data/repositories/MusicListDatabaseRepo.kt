package com.linkjam06.data.repositories

import androidx.annotation.WorkerThread
import com.linkjam06.domain.dao.MusicListDao
import com.linkjam06.domain.models.MusicListModel
import kotlinx.coroutines.flow.Flow

class MusicListDatabaseRepo(private val musicListDao: MusicListDao) {

    val allMusic: Flow<List<MusicListModel>> = musicListDao.getAllMusic()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(music: MusicListModel) {
        musicListDao.insert(music)
    }
}