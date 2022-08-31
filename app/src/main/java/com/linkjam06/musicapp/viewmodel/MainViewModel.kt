package com.linkjam06.musicapp.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.linkjam06.data.apiservice.ApiService
import com.linkjam06.data.repositories.MusicListRepoImpl
import com.linkjam06.domain.models.MusicListModel

class MusicViewModel(private val musicRepo: MusicListRepoImpl )  : ViewModel() {

    val shareLiveData = MutableLiveData<List<MusicListModel>>()

    fun getMusicListData(term: String, offset: Int, limit: Int) : List<MusicListModel> {
        return musicRepo.getSearchMusic(term, offset, limit)
    }

}


class MusicViewModelFactory(private val musicRepo: MusicListRepoImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MusicViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MusicViewModel(musicRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}