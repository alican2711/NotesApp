package com.alicanknt.todo.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class NoteData(
    @PrimaryKey(autoGenerate = true)
    var id :Int,
    var title:String,
    var description:String
    ):Parcelable


