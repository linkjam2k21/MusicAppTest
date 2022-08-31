package com.linkjam06.musicapp.viewmodel


import androidx.lifecycle.*
import com.linkjam06.data.repositories.MusicListDatabaseRepo
import com.linkjam06.domain.models.MusicListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MusicViewModel(private val musicRepo: MusicListDatabaseRepo )  : ViewModel() {
    val allMusic: LiveData<List<MusicListModel>> = musicRepo.allMusic.asLiveData()

    fun insert(music: MusicListModel) = viewModelScope.launch(Dispatchers.IO) {
        musicRepo.insert(music)
    }

}

class MusicViewModelFactory(private val musicRepo: MusicListDatabaseRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MusicViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MusicViewModel(musicRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}