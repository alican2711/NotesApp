package com.alicanknt.todo.repo

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.alicanknt.todo.model.NoteData
import com.alicanknt.todo.service.NoteDao

class NotesRepository(private val noteDao: NoteDao) {

    val getAllData:LiveData<List<NoteData>> = noteDao.getAllData()

    suspend fun insertData(noteData: NoteData){
        noteDao.insertData(noteData)
    }
    suspend fun updateData(noteData: NoteData){
        noteDao.updateData(noteData)
    }
    suspend fun deleteItem(noteData: NoteData){
        noteDao.deleteItem(noteData)
    }
    suspend fun deleteAll(){
        noteDao.deleteAll()
    }
    fun searchDatabase(searcQuery:String):LiveData<List<NoteData>>{
        return noteDao.searchDatabase(searcQuery)
    }
}