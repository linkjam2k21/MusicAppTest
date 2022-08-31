package com.linkjam06.musicapp.userinterface.adapters


import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.linkjam06.domain.models.MusicListModel
import com.linkjam06.musicapp.R


interface AlbumSongItemClickListener {
    fun onAlbumSongItemClickListener(data: MusicListModel)
}

class AlbumSongListAdapter(private val context: Context,
                           private val list: List<MusicListModel>,
                           private val albumSongItemClickListener: AlbumSongItemClickListener
) : RecyclerView.Adapter<AlbumSongListAdapter.AlbumSongsViewHolder>() {

    class AlbumSongsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val trackNumberItemView: TextView = itemView.findViewById(R.id.trackNumber)
        val trackNameItemView: TextView = itemView.findViewById(R.id.trackName)
        val trackTimeItemView: TextView = itemView.findViewById(R.id.trackTime)
        val playButtonItemView: FrameLayout = itemView.findViewById(R.id.playButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumSongsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.albumsonglist_item, parent, false)
        return AlbumSongsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: AlbumSongsViewHolder, position: Int) {
        val music = list[position]

        holder.trackNumberItemView.text = music.trackNumber.toString()
        holder.trackNameItemView.text = music.trackName
        holder.trackTimeItemView.text = music.trackTime

        if(holder.layoutPosition % 2 == 1)
            holder.itemView.setBackgroundColor(Color.parseColor("#232323"));

        holder.playButtonItemView.setOnClickListener {
            albumSongItemClickListener.onAlbumSongItemClickListener(music)
        }
    }
}