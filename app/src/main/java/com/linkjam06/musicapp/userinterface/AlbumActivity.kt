package com.linkjam06.musicapp.userinterface

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.linkjam06.data.repositories.MusicListRepoImpl
import com.linkjam06.domain.models.MusicListModel
import com.linkjam06.musicapp.MusicApplication
import com.linkjam06.musicapp.R
import com.linkjam06.musicapp.userinterface.adapters.AlbumSongItemClickListener
import com.linkjam06.musicapp.userinterface.adapters.AlbumSongListAdapter
import com.squareup.picasso.Picasso

class AlbumActivity : AppCompatActivity(), AlbumSongItemClickListener {
    private lateinit var musicRepo: MusicListRepoImpl
    private lateinit var recyclerView: RecyclerView

    private lateinit var imageViewPlay: ImageView
    private lateinit var seekBar: SeekBar
    private lateinit var seekBarHint: TextView

    var seekBarHandler = Handler(Looper.getMainLooper())

    var mediaPlayer: MediaPlayer? = MediaPlayer()
    var wasPlaying = false
    var audioURL: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        //Back Button
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

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
        recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        Thread {
            runOnUiThread {
                getAlbumSongs(collectionID)
            }
        }.start()


        //Audio Player
        mediaPlayer?.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )

        val playButton = findViewById<FrameLayout>(R.id.playButton)
        imageViewPlay = findViewById<ImageView>(R.id.imageViewPlay)
        seekBarHint = findViewById<TextView>(R.id.textViewHint);
        seekBar = findViewById<SeekBar>(R.id.seekbar)

        seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {

                var result = 0

                result = Math.ceil((progress / 1000f).toDouble()).toInt()

                var x = result

                if (x == 0 && mediaPlayer != null && mediaPlayer!!.isPlaying()) {
                    clearMediaPlayer();

                    imageViewPlay.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@AlbumActivity,
                            android.R.drawable.ic_media_play
                        )
                    )

                    seekBar.progress = 0

                }
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                if (mediaPlayer != null && mediaPlayer!!.isPlaying()) {
                    try {
                        mediaPlayer!!.seekTo(seekBar.progress);
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

            }
        })

        playButton.setOnClickListener {
            playSong(audioURL!!)
        }

    }

    override fun onAlbumSongItemClickListener(data: MusicListModel) {
        audioURL = data.previewSong
        findViewById<TextView>(R.id.trackNamePlay).text = data.trackName
        clearMediaPlayer()

        playSong(audioURL!!)

    }

    fun getAlbumSongs(collectionId: Long) {
        var data = musicRepo.getSearchAlbumSongs(collectionId)
        data = data.filter { x -> x.kind == "song" }
        recyclerView.adapter = AlbumSongListAdapter(this, data, this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        clearMediaPlayer()
    }

    //Media Player Controls
    fun playSong(audioUrl: String) {
        try {
            findViewById<LinearLayout>(R.id.player).visibility = View.VISIBLE

            if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                clearMediaPlayer()
                findViewById<LinearLayout>(R.id.player).visibility = View.GONE

                try {
                    seekBar.progress = 0
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                wasPlaying = true
                imageViewPlay.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        android.R.drawable.ic_media_play
                    )
                )
            }

            if (!wasPlaying) {
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer()
                }
                imageViewPlay.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        android.R.drawable.ic_media_pause
                    )
                )

                mediaPlayer!!.setDataSource(audioUrl)
                mediaPlayer!!.prepare()

                mediaPlayer!!.isLooping = false

                try {
                    seekBar.max = mediaPlayer!!.duration
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                mediaPlayer!!.start()

                seekBarHandler.removeCallbacks(seekBarRunnable)
                seekBarHandler.postDelayed(seekBarRunnable, 15)

            }
            wasPlaying = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val seekBarRunnable: Runnable by lazy {
        Runnable {
            try {
                val totalDuration = mediaPlayer!!.duration.toLong()
                val currentDuration = mediaPlayer!!.currentPosition.toLong()

                seekBarHint.text = "" + milliSecondsToTimer(totalDuration - currentDuration)

                try {
                    seekBar.setProgress(currentDuration.toInt())
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (seekBar.max != seekBar.progress)
                seekBarHandler.postDelayed(seekBarRunnable, 15)

        }
    }

    fun milliSecondsToTimer(milliseconds: Long): String? {
        var finalTimerString = ""
        var secondsString = ""

        // Convert total duration into time
        val hours = (milliseconds / (1000 * 60 * 60)).toInt()
        val minutes = (milliseconds % (1000 * 60 * 60)).toInt() / (1000 * 60)
        val seconds = (milliseconds % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()
        // Add hours if there
        if (hours > 0) {
            finalTimerString = "$hours:"
        }

        // Prepending 0 to seconds if it is one digit
        secondsString = if (seconds < 10) {
            "0$seconds"
        } else {
            "" + seconds
        }
        finalTimerString = "$finalTimerString$minutes:$secondsString"

        // return timer string
        return finalTimerString
    }

    private fun clearMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            mediaPlayer = null
        }
    }
}