package com.example.bookshelf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.bookshelf.databinding.ActivityEditBinding
import com.example.bookshelf.db.Book
import com.example.bookshelf.db.BookDatabase
import com.example.bookshelf.db.BookRepository

class EditActivity : AppCompatActivity() {
    private lateinit var bookViewModel: BookViewModel
    private lateinit var binding: ActivityEditBinding
    private lateinit var book: Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit)
        val dao = BookDatabase.getInstance(application).bookDAO
        val repository = BookRepository(dao)
        val factory = BookViewModelFactory(repository)
        bookViewModel = ViewModelProvider(this,factory).get(BookViewModel::class.java)
        binding.myViewModel = bookViewModel
        binding.lifecycleOwner = this

        val intent = intent.getStringExtra("EDIT")
        val parts = intent?.split(", ")
        book = Book(
            parts?.get(0)?.toInt() ?:0,
            parts?.get(1) ?:"" ,
            parts?.get(2) ?:"",
            parts?.get(3)?:"",
            parts?.get(4)?:"")
        bookViewModel.editInputAuthor.value = book.author
        bookViewModel.editInputTitle.value = book.title
        bookViewModel.editInputComment.value = book.comment
        bookViewModel.editInputRead.value = book.read

        binding.editButton.setOnClickListener {
            bookViewModel.edit(book)
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        binding.deleteButton.setOnClickListener {
            bookViewModel.delete(book)
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
}