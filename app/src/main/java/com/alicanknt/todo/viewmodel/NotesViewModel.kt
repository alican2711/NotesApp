package com.alicanknt.todo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.alicanknt.todo.model.NoteData
import com.alicanknt.todo.repo.NotesRepository
import com.alicanknt.todo.service.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application):AndroidViewModel(application) {
    private val noteDao = NotesDatabase.getDatabase(application).noteDao()
     val repository:NotesRepository
    val getAllData :LiveData<List<NoteData>>

    init {
        repository = NotesRepository(noteDao)
        getAllData = repository.getAllData
    }
    fun insertData(noteData: NoteData){
       viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(noteData)

        }
    }
    fun updateData(noteData: NoteData){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateData(noteData)
        }
    }
    fun deleteItem(noteData: NoteData){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteItem(noteData)
        }
    }
    fun deleteAll(){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteAll()
        }
    }
    fun searchDatabase(searchQuery: String):LiveData<List<NoteData>>{
        return noteDao.searchDatabase(searchQuery)
    }

    }