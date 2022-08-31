package com.linkjam06.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "music")
data class MusicListModel (
        @PrimaryKey @ColumnInfo(name = "word")
        val trackId: Long = 0,
        val imageUrl: String? = null,
        val trackNumber: Long = 0,
        val trackName: String? = null,
        val trackTime: String? = null,
        val albumName: String? = null,
        val bandName: String? = null,
        val genreName: String? = null,
        var collectionId: Long =0,
        val previewSong: String? = null,
        val kind: String? = null
)
