package com.linkjam06.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.linkjam06.domain.models.MusicListModel
import kotlinx.coroutines.flow.Flow


@Dao
interface MusicListDao {

    @Query("SELECT * FROM music")
    fun getAllMusic(): Flow<List<MusicListModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(music: MusicListModel)

    @Query("DELETE FROM music")
    fun deleteAll()

}