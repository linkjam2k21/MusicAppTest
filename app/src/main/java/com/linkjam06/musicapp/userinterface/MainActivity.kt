package com.linkjam06.musicapp.userinterface

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.linkjam06.data.repositories.MusicListRepoImpl
import com.linkjam06.musicapp.MusicApplication
import com.linkjam06.musicapp.R
import com.linkjam06.musicapp.userinterface.adapters.MusicListAdapter
import com.linkjam06.musicapp.viewmodel.MusicViewModel
import com.linkjam06.musicapp.viewmodel.MusicViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var musicViewModel : MusicViewModel

    private lateinit var musicRepo : MusicListRepoImpl

    private  lateinit var progressIndicator: LinearLayout

    val adapter = MusicListAdapter()

    var offSet = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        progressIndicator = findViewById<View>(R.id.progresslinearLayout) as LinearLayout

        musicRepo = (application as MusicApplication).musicRepo

        musicViewModel =
            ViewModelProvider(this, MusicViewModelFactory((application as MusicApplication).musicListDatabaseRepo)).get(MusicViewModel::class.java)

        musicViewModel.allMusic.observe(this, Observer {
            it?.let {
                adapter.submitList(it)
                Toast.makeText(this, "Hi World", Toast.LENGTH_LONG).show()
            }
        })

        // Listado de canciones
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Busqueda de canciones
        val searchView =
            findViewById<View>(R.id.simpleSearchView) as SearchView // inititate a search view

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                getMusicList(query, offSet, 20)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.equals("")){
                    getMusicList("dragon ball", offSet, 20)
                }
                return false
            }
        })

        /*Thread {
            runOnUiThread {
                getMusicList("in utero", offSet, 20)
            }
        }.start()*/

        //musicViewModel.getMusicListData("in situ", 1, 20)
    }

    fun getMusicList(term: String, offSet: Int, limit: Int){
        progressIndicator.visibility = View.VISIBLE
        val data =  musicRepo.getSearchMusic(term, offSet,limit)

        data.forEach {
            musicViewModel.insert(it)
        }

        //adapter.submitList(data)
        progressIndicator.visibility = View.GONE
    }
}