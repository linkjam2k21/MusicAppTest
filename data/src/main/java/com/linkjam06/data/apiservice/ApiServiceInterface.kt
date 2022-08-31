package com.linkjam06.data.apiservice

import androidx.lifecycle.LiveData
import com.linkjam06.data.models.MusicListModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceInterface {

    @GET("/search?mediaType=music")
    fun getSearchMusicList(@Query("term") term: String?, @Query("offset") offset: Int?, @Query("limit") limit: Int?): Call<MusicListModel>

    @GET("/lookup?entity=song")
    fun getSearchAlbumSongs(@Query("id") collectionId: Long): Call<MusicListModel>

}