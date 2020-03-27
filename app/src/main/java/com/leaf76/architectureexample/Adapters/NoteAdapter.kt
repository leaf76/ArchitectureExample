package com.leaf76.architectureexample.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.leaf76.architectureexample.Data.Note
import com.leaf76.architectureexample.R

class NoteAdapter : ListAdapter<Note, NoteAdapter.NoteHolder>(
    DIFF_CALLBACK
) {
    // this is Recycler.Adapter using that was removed
    // private var notes: List<Note> = arrayListOf()

    private lateinit var listener: OnItemClickListener

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.Id == newItem.Id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.title == newItem.title &&
                        oldItem.description == newItem.description &&
                        oldItem.priorty == newItem.priorty
            }
        }
    }

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.text_view_title)
        val textViewDescription: TextView =
            itemView.findViewById(R.id.text_view_description)
        val textViewPriority: TextView = itemView.findViewById(R.id.text_view_priority)
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position))
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        var itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)

        return NoteHolder(itemView)
    }

    // this is Recycler.Adapter using that was removed
//    override fun getItemCount(): Int {
//        return notes.size
//    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote = getItem(position)
        holder.textViewTitle.text = currentNote.title
        holder.textViewDescription.text = currentNote.description
        holder.textViewPriority.text = currentNote.priorty.toString()

    }
// this is Recycler.Adapter using that was removed
//    fun setNotes(notes: List<Note>) {
//        this.notes = notes
//        notifyDataSetChanged()
//    }

    fun getNoteAt(notePosition: Int): Note {
        return getItem(notePosition)
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


}