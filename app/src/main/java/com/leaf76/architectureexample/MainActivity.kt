package com.leaf76.architectureexample

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    companion object {
        const val ADD_NOTE_REQUEST = 1
    }

    private val TAG: String = "MainActivity"

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "MainActivity entry on create")

        val buttonAddNote: FloatingActionButton = findViewById(R.id.button_add_note)
        buttonAddNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)
        }


        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        // set layout manager
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        // set adapter
        val adapter = NoteAdapter()
        recyclerView.adapter = adapter
        Log.i(TAG, "Adapter set OK!")
        // ViewModelProviders.of().get().....was deprecated  on 2.1.0, so change to this
        // Need to add Factory....
        noteViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        )[NoteViewModel::class.java]
        Log.i(TAG, "ModelView set OK!")

        // kotlin live data use
        noteViewModel.getAllNotes().observe(this, Observer<List<Note>> { t: List<Note> ->
            Log.i(TAG, "Get Notes $t")
            adapter.setNotes(t)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "Note deleted", Toast.LENGTH_LONG).show()
            }
        }).attachToRecyclerView(recyclerView)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val title: String = data?.getStringExtra(AddNoteActivity.EXTRA_TITLE) ?: "NoTitle"
            val description: String =
                data?.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION) ?: "No description"
            val priority: Int = data?.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1) ?: 0

            val note = Note(title, description, priority)
            noteViewModel.insert(note)

            Toast.makeText(baseContext, "Note saved", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(baseContext, "Note not saved", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all_notes -> {
                noteViewModel.deleteAllNotes()
                Toast.makeText(baseContext, "All notes deleted", Toast.LENGTH_LONG).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

}
