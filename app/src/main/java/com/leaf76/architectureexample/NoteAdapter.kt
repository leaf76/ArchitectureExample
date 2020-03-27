package com.leaf76.architectureexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    private var notes: List<Note> = arrayListOf()

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.text_view_title)
        val textViewDescription: TextView =
            itemView.findViewById(R.id.text_view_description)
        val textViewPriority: TextView = itemView.findViewById(R.id.text_view_priority)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        var itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)

        return NoteHolder(itemView)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote = notes[position]
        holder.textViewTitle.text = currentNote.title
        holder.textViewDescription.text = currentNote.description
        holder.textViewPriority.text = currentNote.priorty.toString()

    }

    fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    fun getNoteAt(notePosition: Int): Note{
        return notes.get(notePosition)
    }
}