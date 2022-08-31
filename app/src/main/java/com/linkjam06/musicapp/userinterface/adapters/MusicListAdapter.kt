package com.linkjam06.musicapp.userinterface.adapters


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linkjam06.domain.models.MusicListModel
import com.linkjam06.musicapp.R
import com.linkjam06.musicapp.userinterface.AlbumActivity
import com.squareup.picasso.Picasso
import org.json.JSONObject


class MusicListAdapter : ListAdapter<MusicListModel, MusicListAdapter.MusicViewHolder>(UserDiffCallBack()) {

    var onItemClick: ((MusicListModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        return MusicViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val albumNameItemView: TextView = itemView.findViewById(R.id.albumName)
        private val trackNameItemView: TextView = itemView.findViewById(R.id.trackName)
        private val artistName: TextView = itemView.findViewById(R.id.artistName)

        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

         fun bind(music: MusicListModel) {
            albumNameItemView.text = music.albumName
            artistName.text = music.bandName + " " + music.collectionId.toString()
            trackNameItemView.text = music.trackName

            Picasso.get().load(music.imageUrl).into(imageView);

             itemView.setOnClickListener{
                 val intent = Intent(itemView.context, AlbumActivity::class.java).apply {
                     putExtra("collectionID", music.collectionId)
                     putExtra("albumName", music.albumName)
                     putExtra("bandName", music.bandName)
                     putExtra("genreName", music.genreName)
                     putExtra("imageUrl", music.imageUrl)

                 }

                 itemView.context.startActivity(intent)
             }
        }

        companion object {
            fun create(parent: ViewGroup): MusicViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.musiclist_item, parent, false)



                return MusicViewHolder(view)
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