package com.kazim.noteapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kazim.noteapp.model.Note


@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)


    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes ORDER BY id")
    fun getAllNotes():LiveData<List<Note>>

    @Query("SELECT *FROM notes WHERE  noteTitle LIKE :query OR noteBody LIKE :query")
    fun searchNote(query:String?):LiveData<List<Note>>
}