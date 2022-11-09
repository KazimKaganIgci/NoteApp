package com.kazim.noteapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.kazim.noteapp.MainActivity
import com.kazim.noteapp.R
import com.kazim.noteapp.databinding.FragmentUpdateNoteBinding
import com.kazim.noteapp.model.Note
import com.kazim.noteapp.toast
import com.kazim.noteapp.viewModel.NoteViewModel


class UpdateNoteFragment : Fragment() {
    private var _binding:FragmentUpdateNoteBinding ?=null
    private val binding get() = _binding!!
    private lateinit var noteViewModel: NoteViewModel
    private val args:UpdateNoteFragmentArgs by navArgs()
    private lateinit var CurrentNote: Note


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        _binding =FragmentUpdateNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel =(activity as MainActivity).noteViewModel
        CurrentNote =args.note!!
        binding.etNoteBodyUpdate.setText(CurrentNote.noteBody)
        binding.etNoteTitleUpdate.setText(CurrentNote.noteTitle)

        binding.fabDone.setOnClickListener {
            val title =binding.etNoteTitleUpdate.text.toString().trim()
            val body =binding.etNoteBodyUpdate.text.toString().trim()
            if(title.isNotEmpty()){
                val note =Note(CurrentNote.id,title,body)
                noteViewModel.updateNote(note)
                activity?.toast("Not güncellendi!")

                view.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
            }else{
                activity?.toast("Lütfen bir başlık giriniz!")
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.update_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)

    }
    private fun deleteNote(){
        AlertDialog.Builder(activity).apply {
            setTitle("Notu sil")
            setMessage("Notu silmek istediğine emin misin ?")
            setPositiveButton("SIL"){_,_->
                noteViewModel.deleteNote(CurrentNote)
                view?.findNavController()?.navigate(R.id.action_updateNoteFragment_to_homeFragment)
            }
            setNegativeButton("IPTAL ET",null)
        }.create().show()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete_menu->{
                deleteNote()
            }
        }
        return super.onOptionsItemSelected(item)

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding =null
    }


}