package com.leaf76.architectureexample

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class NoteRepository(application: Application) {
    // The lateinit avoid Nullable
    private var noteDao: NoteDao

    private var allNotes: LiveData<List<Note>>

    init {
        val database = NoteDatabase.getInstance(application.applicationContext)
        noteDao = database.noteDao()
        allNotes = noteDao.getAllNotes()
    }

    fun insert(note: Note) {
        InsertNoteAsyncTask(noteDao).execute(note)
    }

    fun update(note: Note) {
        UpdateNoteAsyncTask(noteDao).execute(note)
    }

    fun delete(note: Note) {
        DeleteNoteAsyncTask(noteDao).execute(note)
    }

    fun deleteAllnotes() {
        DeleteAllNotesAsyncTask(noteDao).execute()
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }

    companion object {
        // Insert note
        private class InsertNoteAsyncTask(var noteDao: NoteDao) :
            AsyncTask<Note, Unit, Unit>() {

            override fun doInBackground(vararg notes: Note) {
                noteDao.insert(notes[0])
            }
        }

        // Update note
        private class UpdateNoteAsyncTask(var noteDao: NoteDao) :
            AsyncTask<Note, Unit, Unit>() {

            override fun doInBackground(vararg notes: Note){
                noteDao.update(notes[0])
            }
        }

        // Delete note
        private class DeleteNoteAsyncTask(var noteDao: NoteDao) :
            AsyncTask<Note, Unit, Unit>() {

            override fun doInBackground(vararg notes: Note) {
                noteDao.delete(notes[0])
            }
        }

        // Delete all note
        private class DeleteAllNotesAsyncTask(var noteDao: NoteDao) :
            AsyncTask<Unit, Unit, Unit>() {

            override fun doInBackground(vararg units: Unit) {
                noteDao.deleteAllNotes()
            }
        }
    }

}