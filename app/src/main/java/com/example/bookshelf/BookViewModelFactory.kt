package com.example.bookshelf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookshelf.db.BookRepository
import java.lang.IllegalArgumentException

class BookViewModelFactory(
    private val repository: BookRepository
    ):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookViewModel::class.java)){
            return BookViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}