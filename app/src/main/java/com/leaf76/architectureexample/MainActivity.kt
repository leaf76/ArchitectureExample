package com.leaf76.architectureexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "MainActivity entry on create")

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

    }
}
