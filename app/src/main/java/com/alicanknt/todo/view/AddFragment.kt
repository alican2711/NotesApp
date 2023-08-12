package com.alicanknt.todo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alicanknt.todo.R
import com.alicanknt.todo.databinding.FragmentAddBinding
import com.alicanknt.todo.model.NoteData
import com.alicanknt.todo.viewmodel.NotesViewModel
import com.alicanknt.todo.viewmodel.SharedViewModel


class AddFragment : Fragment() {
    private lateinit var binding : FragmentAddBinding
    private val `view-model`:NotesViewModel by viewModels ()
    private val sharedViewModel: SharedViewModel by viewModels()





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater,container,false)


        return binding.root
    }


        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            binding.toolbarAdd.title="Add"
            (activity as AppCompatActivity).setSupportActionBar(binding.toolbarAdd)
            val menuHost: MenuHost =requireActivity()
            menuHost.addMenuProvider(object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.add_menu,menu)


                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    if (menuItem.itemId == R.id.menu_save){
                        insertDatabase()
                    }
                    return true
                }


            },viewLifecycleOwner, Lifecycle.State.RESUMED)
        }

    private fun insertDatabase() {
        val mTitle = binding.title.text.toString()
        val description =binding.description.text.toString()
        val validation = sharedViewModel.verifyDataFromUser(mTitle,description)
        if (validation){
            val newData= NoteData(
                0,
                mTitle,
                description
            )
            `view-model`.insertData(newData)
            Toast.makeText(requireContext(),"Succes Added..",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(),"Please fill out all fields",Toast.LENGTH_SHORT).show()

        }


    }


}

