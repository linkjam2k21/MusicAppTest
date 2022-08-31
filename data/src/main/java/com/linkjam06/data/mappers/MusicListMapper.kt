package com.linkjam06.data.mappers

import com.linkjam06.domain.models.MusicListModel
import com.linkjam06.data.models.MusicListResult as DataMusicListModel

class MusicListMapper {
    fun toMusicList(shareModelServer: DataMusicListModel): MusicListModel {

        val milliseconds: Long = shareModelServer.trackTimeMillis

        val minutes = milliseconds / 1000 / 60
        val seconds = milliseconds / 1000 % 60
        var secondsStr = seconds.toString();

        if(seconds<10)
            secondsStr = "0" + seconds.toString()

        return MusicListModel(
            trackId = shareModelServer.trackId,
            bandName = shareModelServer.artistName,
            trackName = shareModelServer.trackName,
            imageUrl = shareModelServer.artworkUrl100,
            albumName = shareModelServer.collectionName,
            collectionId = shareModelServer.collectionId,
            trackNumber = shareModelServer.trackNumber,
            trackTime = minutes.toString() + ":" + secondsStr,
            genreName = shareModelServer.primaryGenreName + " - " + shareModelServer.releaseDate.substring(0,4),
            previewSong = shareModelServer.previewUrl,
            kind = shareModelServer.kind
        )
    }
}