package com.leaf76.architectureexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "MainActivity entry on create")

//      ViewModelProviders.of().get().....was deprecated  on 2.1.0, so change to this
        noteViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        )
            .get(NoteViewModel::class.java)

        // Kotlin write
//        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        Log.i(TAG, "Get note view model")
        // kotlin live data use
        noteViewModel.getAllNotes().observe(this, Observer<List<Note>> {
//            t: List<Note>? ->
            Toast.makeText(this, "onChanged", Toast.LENGTH_LONG).show()
        })
        Log.i(TAG, "Get Toast")
    }
}
