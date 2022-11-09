package com.kazim.noteapp.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kazim.noteapp.MainActivity
import com.kazim.noteapp.R
import com.kazim.noteapp.databinding.FragmentNewNoteBinding
import com.kazim.noteapp.model.Note
import com.kazim.noteapp.toast
import com.kazim.noteapp.viewModel.NoteViewModel

class NewNoteFragment : Fragment(R.layout.fragment_new_note) {
    private var _binding:FragmentNewNoteBinding ?=null
    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var mView:View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding =FragmentNewNoteBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel=(activity as MainActivity).noteViewModel
        mView =view
    }
    private fun saveNote(view: View){
        val noteTitle =binding.etNoteTitle.text.toString().trim()
        val noteBody =binding.etNoteBody.text.toString().trim()
        if (noteTitle.isNotEmpty()){
            val note = Note(0,noteTitle,noteBody)
            noteViewModel.addNote(note)
            Snackbar.make(view,"Not başarıyla kayıt edildi.",Snackbar.LENGTH_SHORT).show()
            view.findNavController().navigate(R.id.action_newNoteFragment_to_homeFragment)
        }else{
           activity?.toast("Lütfen bir başlık girin!")
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.save_menu ->{
                saveNote(mView)
            }

        }
        return super.onOptionsItemSelected(item)

    }







    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.new_note_menu,menu)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}