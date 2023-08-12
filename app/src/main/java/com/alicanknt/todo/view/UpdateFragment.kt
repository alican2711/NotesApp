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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alicanknt.todo.R
import com.alicanknt.todo.databinding.FragmentUpdateBinding
import com.alicanknt.todo.model.NoteData
import com.alicanknt.todo.viewmodel.NotesViewModel
import com.alicanknt.todo.viewmodel.SharedViewModel


class UpdateFragment : Fragment() {
    private lateinit var binding: FragmentUpdateBinding
    private val args by navArgs<UpdateFragmentArgs>()
    private val sharedViewModel: SharedViewModel by viewModels()
    private val notesViewModel: NotesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.args = args

        binding.currentTitle.setText(args.currentitem.title)
        binding.currentDescription.setText(args.currentitem.description)




        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarUpdate.title = "Update"
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarUpdate)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.update_menu, menu)


            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {


                if (menuItem.itemId ==   R.id.action_update) {
                     updateItemm()

                }else if (menuItem.itemId == R.id.action_delete){
                    confirmRemovalItem()
                }
                return true
            }


        },viewLifecycleOwner,Lifecycle.State.RESUMED)
    }



    private fun confirmRemovalItem() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            notesViewModel.deleteItem(args.currentitem)
            Toast.makeText(
                requireContext(),
                "Succesfully removed..: ${args.currentitem.title}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentitem.title}?")
        builder.setMessage("Are you sure you want to remove ${args.currentitem.title}?")
        builder.create().show()
    }








    private fun updateItemm() {
        val title = binding.currentTitle.text.toString()
        val description = binding.currentDescription.text.toString()
        val validation = sharedViewModel.verifyDataFromUser(title, description)
        if (validation){
            val updateItem = NoteData(
                args.currentitem.id,
                title,
                description
            )
            notesViewModel.updateData(updateItem)
            Toast.makeText(requireContext(),"Succesfully update..",Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)

        }else{
            Toast.makeText(requireContext(),"Please fill out all fields..",Toast.LENGTH_LONG).show()


        }

    }





}