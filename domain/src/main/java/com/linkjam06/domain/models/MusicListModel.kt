package com.linkjam06.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "music")
class MusicListModel  (
        @PrimaryKey @ColumnInfo(name = "trackId")
        var trackId: Long,
        var imageUrl: String,
        var trackNumber: Long,
        var trackName: String,
        var trackTime: String,
        var albumName: String,
        var bandName: String,
        var genreName: String,
        var collectionId: Long,
        var previewSong: String,
        var kind: String
)