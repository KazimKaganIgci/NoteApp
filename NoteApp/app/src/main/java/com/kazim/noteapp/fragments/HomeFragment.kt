package com.kazim.noteapp.fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kazim.noteapp.MainActivity
import com.kazim.noteapp.R
import com.kazim.noteapp.adapter.NoteAdapter
import com.kazim.noteapp.databinding.FragmentHomeBinding
import com.kazim.noteapp.model.Note
import com.kazim.noteapp.viewModel.NoteViewModel


class HomeFragment : Fragment(R.layout.fragment_home),SearchView.OnQueryTextListener {
    private var _binding:FragmentHomeBinding ?=null
    private val binding get() = _binding!!
    private lateinit var noteViewModel:NoteViewModel
    private lateinit var noteAdapter:NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding =FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel=(activity as MainActivity).noteViewModel
        setupRecyclerview()
        binding.fabAddNote.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)

        }



    }
    private fun setupRecyclerview(){
        noteAdapter = NoteAdapter()
        binding.recyclerView.apply {
            layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter =noteAdapter
        }
        activity?.let {
            noteViewModel.getAllNotes().observe(viewLifecycleOwner) {note->
                noteAdapter.differ.submitList(note)
                updateNoteUI(note)

            }
        }
    }

    private fun updateNoteUI(note: List<Note>) {
        if (note.isNotEmpty()){
            binding.recyclerView.visibility=View.VISIBLE
            binding.tvNoNoteAvailable.visibility=View.GONE

        }else{
            binding.recyclerView.visibility=View.GONE
            binding.tvNoNoteAvailable.visibility=View.VISIBLE
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.home_menu,menu)
        val mMenuSearch=menu.findItem(R.id.menu_search).actionView as SearchView
        mMenuSearch.isSubmitButtonEnabled =true
        mMenuSearch.setOnQueryTextListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding =null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query!=null){
            searchNote(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText==null){
            searchNote(newText)
        }
        return true
    }
    private fun searchNote(query: String?){
        val searchQuery ="%$query%"
        noteViewModel.searchNote(searchQuery).observe(this,{list->
            noteAdapter.differ.submitList(list)
        })
    }


}