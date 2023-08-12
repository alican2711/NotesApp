package com.alicanknt.todo.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.alicanknt.todo.model.NoteData

class SharedViewModel(application: Application):AndroidViewModel(application) {
    val emptyData =MutableLiveData<Boolean>(false)

    fun chehIfDataBaseEmpty(noteData: List<NoteData>){
        emptyData.value =noteData.isEmpty()
    }


    fun verifyDataFromUser(title:String,description:String):Boolean{
        return  if (TextUtils.isEmpty(title)|| TextUtils.isEmpty(description)){
            false

        }else!(title.isEmpty() || description.isEmpty())

    }
}