package com.alicanknt.todo.view

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
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
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alicanknt.todo.R
import com.alicanknt.todo.adapter.NotesAdapter
import com.alicanknt.todo.adapter.SwipeToDelete
import com.alicanknt.todo.databinding.FragmentListBinding
import com.alicanknt.todo.model.NoteData
import com.alicanknt.todo.viewmodel.NotesViewModel
import com.alicanknt.todo.viewmodel.SharedViewModel
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.LandingAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlin.system.exitProcess


class ListFragment : Fragment(),SearchView.OnQueryTextListener {

    private lateinit var binding :FragmentListBinding
    private var notesAdapter = NotesAdapter()
    private val sharedViewModel :  SharedViewModel by viewModels()

    private val notesViewModel : NotesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater,container,false)
        binding.lifecycleOwner =this
        binding.sharedViewModel =sharedViewModel
        receyclerView()



        notesViewModel.getAllData.observe(viewLifecycleOwner) { data ->
            sharedViewModel.chehIfDataBaseEmpty(data)
            notesAdapter.setData(data)
            binding.recyclerView.scheduleLayoutAnimation()
        }





        binding.listLayout.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }

        return binding.root
    }
    private fun receyclerView(){
        val recyclerView =binding.recyclerView
        recyclerView.adapter =notesAdapter
        recyclerView.layoutManager =StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerView.itemAnimator =SlideInUpAnimator().apply {
            addDuration =300

        }
        swipteToDelete(recyclerView)
    }


    private fun swipteToDelete(recyclerView: RecyclerView){
        val swipeCallback = object :SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val  deleteItem =notesAdapter.dataList[viewHolder.adapterPosition]
                notesViewModel.deleteItem(deleteItem)

                restoreDeleteItem(viewHolder.itemView,deleteItem,viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
    private fun restoreDeleteItem(view: View,deletItem:NoteData,position:Int){
        val snackbar = Snackbar.make(
            view,"Deleted '${deletItem.title}'",Snackbar.LENGTH_LONG
        )
        snackbar.setAction("Previous"){
            notesViewModel.insertData(deletItem)
            notesAdapter.notifyItemChanged(position)
        }
        snackbar.show()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarList.title="NOTE"
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarList)
        val menuHost:MenuHost =requireActivity()
        menuHost.addMenuProvider(object :MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.list_menu,menu)
                val search =menu.findItem(R.id.menu_search)
                val searchView = search.actionView as? SearchView
                searchView?.isSubmitButtonEnabled =true
                searchView?.setOnQueryTextListener(this@ListFragment)
                

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if(menuItem.itemId ==  R.id.action_delete ){
                 confirmRemoval()

                }else if (menuItem.itemId == R.id.action_exit){
                    exit()


                }
                return true
            }


        },viewLifecycleOwner,Lifecycle.State.RESUMED)
    }
    private fun exit(){
        exitProcess(0)
    }

    private fun confirmRemoval() {
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            notesViewModel.deleteAll()
            Toast.makeText(
                requireContext(),
                "Successfully Removed Everything!",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to remove everything?")
        builder.create().show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query!=null){
            searchThorughDatabase(query)
        }
        return true

    }

    private fun searchThorughDatabase(query: String) {
        var searchQuerry= "%$query"

        notesViewModel.searchDatabase(searchQuerry).observe(viewLifecycleOwner, Observer { list->
            list?.let {
                notesAdapter.setData(it)
            }
        })
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText!=null){
            searchThorughDatabase(newText)
        }
        return true

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding ==null
    }
}