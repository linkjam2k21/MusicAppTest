package com.linkjam06.musicapp

import android.app.Application
import android.content.Context
import com.linkjam06.data.apiservice.ApiService
import com.linkjam06.data.mappers.MusicListMapper
import com.linkjam06.data.repositories.MusicListRepoImpl

class MusicApplication : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    val musicRepo by lazy { MusicListRepoImpl(ApiService(), MusicListMapper()) }

}