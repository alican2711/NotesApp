package com.alicanknt.todo.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alicanknt.todo.model.NoteData


@Database(entities = [NoteData::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {


        abstract fun noteDao():NoteDao


        companion object{
            @Volatile private var instance :NotesDatabase?=null
            fun getDatabase(context: Context):NotesDatabase{
                val temInstance = instance
                if (temInstance!=null){
                    return temInstance
                }
                synchronized(this){
                    val instancee = Room.databaseBuilder(
                        context.applicationContext,
                        NotesDatabase::class.java,
                        "note_database"
                    ).build()
                    instance=instancee
                    return instancee
                }
                
            }

            }






        }

