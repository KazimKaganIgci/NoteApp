package com.kazim.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.kazim.noteapp.databinding.ActivityMainBinding
import com.kazim.noteapp.db.NoteDatabase
import com.kazim.noteapp.repository.NoteRepository
import com.kazim.noteapp.viewModel.NoteViewModel
import com.kazim.noteapp.viewModel.NoteViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    lateinit var noteViewModel: NoteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        setSupportActionBar(binding.toolbar)


    }

    private fun setupViewModel() {
        val noteRepository=NoteRepository(NoteDatabase(this))
        val viewModelProvider =NoteViewModelProviderFactory(application,noteRepository)
        noteViewModel =ViewModelProvider(this,viewModelProvider).get(NoteViewModel::class.java)
    }
}




















































