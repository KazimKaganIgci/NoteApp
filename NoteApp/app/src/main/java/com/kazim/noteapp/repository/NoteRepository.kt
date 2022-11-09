package com.kazim.noteapp.repository

import androidx.room.Query
import com.kazim.noteapp.db.NoteDatabase
import com.kazim.noteapp.model.Note

class NoteRepository(private val db:NoteDatabase) {

    suspend fun addNote(note: Note) =db.getNoteDao().addNote(note)
    suspend fun deleteNote(note: Note)=db.getNoteDao().deleteNote(note)
    suspend fun updateNote(note: Note)=db.getNoteDao().updateNote(note)
    fun getAllNotes()=db.getNoteDao().getAllNotes()
    fun searchNote(query:String) =db.getNoteDao().searchNote(query)

}