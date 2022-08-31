package com.linkjam06.musicapp.userinterface

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.linkjam06.data.repositories.MusicListRepoImpl
import com.linkjam06.musicapp.MusicApplication
import com.linkjam06.musicapp.R
import com.linkjam06.musicapp.userinterface.adapters.AlbumSongListAdapter
import com.linkjam06.musicapp.userinterface.adapters.MusicListAdapter
import com.squareup.picasso.Picasso

class AlbumActivity : AppCompatActivity() {
    private lateinit var musicRepo : MusicListRepoImpl
    private val adapter = AlbumSongListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        musicRepo = (application as MusicApplication).musicRepo

        val collectionID = getIntent()?.getLongExtra("collectionID", 0)!!
        val albumName = getIntent()?.getStringExtra("albumName")!!
        val bandName = getIntent()?.getStringExtra("bandName")!!
        val genreName = getIntent()?.getStringExtra("genreName")!!
        val imageUrl = getIntent()?.getStringExtra("imageUrl")!!

        findViewById<TextView>(R.id.albumName).text = albumName
        findViewById<TextView>(R.id.bandName).text = bandName
        findViewById<TextView>(R.id.genreName).text = genreName

        val imageView = findViewById<ImageView>(R.id.imageView)
        Picasso.get().load(imageUrl).into(imageView)

        // Listado de canciones
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        Thread {
            runOnUiThread {
                getAlbumSongs(collectionID)
            }
        }.start()
    }

    fun getAlbumSongs(collectionId: Long){
        var data =  musicRepo.getSearchAlbumSongs(collectionId)
        //"kind":"song"
        data = data.filter { x -> x.kind == "song" }

        adapter.submitList(data)
    }
}