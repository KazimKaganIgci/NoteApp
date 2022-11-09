package com.kazim.noteapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.kazim.noteapp.model.Note
import com.kazim.noteapp.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(app:Application,private val noteRepository: NoteRepository) :AndroidViewModel(app){



    fun addNote(note: Note)=viewModelScope.launch {
        noteRepository.addNote(note)
    }
    fun deleteNote(note: Note)=viewModelScope.launch {
        noteRepository.deleteNote(note)
    }
    fun updateNote(note: Note)=viewModelScope.launch {
        noteRepository.updateNote(note)
    }
    fun getAllNotes()=noteRepository.getAllNotes()

    fun searchNote(query:String) =noteRepository.searchNote(query)
}