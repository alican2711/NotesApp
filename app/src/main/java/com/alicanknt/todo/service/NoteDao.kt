package com.alicanknt.todo.service

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alicanknt.todo.model.NoteData

@Dao
interface NoteDao {

        //tüm veriler alınıcak
         @Query ("SELECT*FROM NoteData ORDER BY id ASC")
        fun getAllData():LiveData<List<NoteData>>

        //aynı olan verilere göz yumulan ilk kod
        @Insert(onConflict = OnConflictStrategy.IGNORE)
       suspend fun insertData(noteData: NoteData)



       @Update
       suspend fun updateData(noteData: NoteData)



       @Delete
       suspend fun deleteItem(noteData: NoteData)


       @Query("DELETE FROM NoteData")
       suspend fun deleteAll()
        @Query("SELECT * FROM NoteData WHERE title LIKE:searchQuery")
       fun searchDatabase(searchQuery: String):LiveData<List<NoteData>>



}