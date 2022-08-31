package com.linkjam06.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.linkjam06.domain.dao.MusicListDao
import com.linkjam06.domain.models.MusicListModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(MusicListModel::class), version = 1, exportSchema = false)
abstract class MusicRoomDataBase : RoomDatabase() {

    abstract fun musicListDao(): MusicListDao

    private class MusicDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    //populateDatabase(database.musicListDao())
                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MusicRoomDataBase? = null

        fun getDatabase(context: Context,  scope: CoroutineScope): MusicRoomDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MusicRoomDataBase::class.java,
                    "music_database"
                ).addCallback(MusicDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

