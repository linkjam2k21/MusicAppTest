package com.linkjam06.data.repositories

import com.linkjam06.data.apiservice.ApiService
import com.linkjam06.data.mappers.MusicListMapper
import com.linkjam06.domain.models.MusicListModel
import com.linkjam06.domain.repositories.ItunesMusicRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MusicListRepoImpl constructor(
    private val apiService: ApiService,
    private val shareMapper: MusicListMapper) : ItunesMusicRepo {

    override fun getSearchMusic(term: String, offset: Int, limit: Int): List<MusicListModel> {
        var call = apiService.getApiClient()?.getSearchMusicList(term, offset, limit)
        var res = call?.execute()?.body()

        return res?.results!!.map {
              shareMapper.toMusicList(it)
        }

    }
    override fun getSearchAlbumSongs(collectionId: Long): List<MusicListModel> {
        var call = apiService.getApiClient()?.getSearchAlbumSongs(collectionId)
        var res = call?.execute()?.body()

        return res?.results!!.map {
            shareMapper.toMusicList(it)
        }

    }

}