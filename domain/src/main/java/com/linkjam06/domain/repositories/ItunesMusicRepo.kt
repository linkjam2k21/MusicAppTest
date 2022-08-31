package com.linkjam06.domain.repositories

import com.linkjam06.domain.models.MusicListModel

interface ItunesMusicRepo {
    fun getSearchMusic(term: String, offset: Int,  limit: Int): List<MusicListModel>
    fun getSearchAlbumSongs(collectionId: Long): List<MusicListModel>
}