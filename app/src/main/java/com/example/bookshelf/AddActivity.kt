package com.example.bookshelf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.bookshelf.databinding.ActivityAddBinding

import com.example.bookshelf.db.BookDatabase
import com.example.bookshelf.db.BookRepository


class AddActivity : AppCompatActivity() {
    private lateinit var bookViewModel: BookViewModel
    private lateinit var binding: ActivityAddBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)
        val dao = BookDatabase.getInstance(application).bookDAO
        val repository = BookRepository(dao)
        val factory = BookViewModelFactory(repository)
        bookViewModel = ViewModelProvider(this,factory).get(BookViewModel::class.java)
        binding.myViewModel = bookViewModel
        binding.lifecycleOwner = this

        binding.saveButton.setOnClickListener {
            bookViewModel.save()
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        binding.cancelButton.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

    }
}