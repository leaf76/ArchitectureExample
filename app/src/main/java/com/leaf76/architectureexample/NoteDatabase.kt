package com.leaf76.architectureexample

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    // static parameters
    companion object {

        private lateinit var instance: NoteDatabase

        fun getInstance(context: Context): NoteDatabase {

            synchronized(NoteDatabase::class.java) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java, "note_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallbackL)
                    .build()
            }

            return instance

        }

        private val roomCallbackL: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance).execute()
            }
        }

        private class PopulateDbAsyncTask(db: NoteDatabase) : AsyncTask<Unit, Unit, Unit>() {

            private val noteDao: NoteDao = db.noteDao()

            override fun doInBackground(vararg units: Unit) {
                noteDao.insert(Note("Title1", "Description1", 1))
                noteDao.insert(Note("Title2", "Description2", 2))
                noteDao.insert(Note("Title3", "Description3", 3))
            }
        }
    }
}