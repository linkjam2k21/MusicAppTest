package com.linkjam06.musicapp.userinterface.adapters


import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linkjam06.domain.models.MusicListModel
import com.linkjam06.musicapp.R
import com.linkjam06.musicapp.userinterface.AlbumActivity
import com.squareup.picasso.Picasso

class AlbumSongListAdapter : ListAdapter<MusicListModel, AlbumSongListAdapter.AlbumSongsViewHolder>(UserDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumSongsViewHolder {
        return AlbumSongsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AlbumSongsViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class AlbumSongsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val trackNumberItemView: TextView = itemView.findViewById(R.id.trackNumber)
        private val trackNameItemView: TextView = itemView.findViewById(R.id.trackName)
        private val trackTimeItemView: TextView = itemView.findViewById(R.id.trackTime)

        private val playButtonItemView: FrameLayout = itemView.findViewById(R.id.playButton)

        fun bind(music: MusicListModel) {
            trackNumberItemView.text = music.trackNumber.toString()
            trackNameItemView.text = music.trackName
            trackTimeItemView.text = music.trackTime

            if(layoutPosition % 2 == 1)
                itemView.setBackgroundColor(Color.parseColor("#232323"));

            playButtonItemView.setOnClickListener {

            }
        }

        companion object {
            fun create(parent: ViewGroup): AlbumSongsViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.albumsonglist_item, parent, false)

                return AlbumSongsViewHolder(view)
            }
        }

    }

    private class UserDiffCallBack : DiffUtil.ItemCallback<MusicListModel>() {
        override fun areItemsTheSame(oldItem: MusicListModel, newItem: MusicListModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MusicListModel, newItem: MusicListModel): Boolean {
            return oldItem.trackId == newItem.trackId
        }
    }

}