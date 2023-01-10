package com.example.notesapproom

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.example.notesapproom.databinding.ActivityMainBinding
import com.example.notesapproom.databinding.DialogUpdateNoteBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var listNotes = listOf<Note>()
    private lateinit var appDb: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDb = AppDatabase.getDatabase(this)
        getNotes()

        binding.btnSubmit.setOnClickListener {
            add()
        }

    }

    private fun getNotes() {
        GlobalScope.launch(Dispatchers.IO) {
            listNotes = appDb.NotesDao().getAll()
            binding.recNotes.adapter = NotesAdapter(this@MainActivity,listNotes){ note, status ->
                if (status == "Update") {
                    showDialog(note)
                } else if (status == "Delete") {
                    delete(note)
                }
            }
        }
    }

    private fun add() {
        val noteMessage = binding.editNoteMessage.text.toString()
        if (noteMessage.isNotEmpty()) {
            val note = Note(null, noteMessage)
            GlobalScope.launch(Dispatchers.IO) {
                appDb.NotesDao().insert(note)
            }
            binding.editNoteMessage.text.clear()
            Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Please enter data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun delete(note: Note) {
        GlobalScope.launch(Dispatchers.IO) {
            appDb.NotesDao().delete(note)
        }
    }

    private fun update(note: Note) {
        GlobalScope.launch(Dispatchers.IO) {
            appDb.NotesDao().update(note)
        }
    }

    private fun showDialog(oldNote: Note) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dialogBinding = DialogUpdateNoteBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.btnSave.setOnClickListener {
            if (dialogBinding.editText.text.toString().isNotEmpty()) {
                val note = Note(oldNote.noteId,dialogBinding.editText.text.toString())
                update(note)
                dialog.dismiss()
            } else {
                dialogBinding.editText.error = "Can not add empty note"
            }
        }
        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

}