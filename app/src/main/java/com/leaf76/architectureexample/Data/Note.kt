package com.leaf76.architectureexample.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(var title: String, var description: String, var priorty: Int) {

    @PrimaryKey(autoGenerate = true)
    var Id: Int = 0
}