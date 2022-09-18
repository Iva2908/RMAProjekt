package com.example.bookshelf

import androidx.lifecycle.*
import com.example.bookshelf.db.Book
import com.example.bookshelf.db.BookRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BookViewModel(private val repository: BookRepository):ViewModel() {
    val inputAuthor = MutableLiveData<String>()
    val inputTitle = MutableLiveData<String>()
    val inputComment = MutableLiveData<String>()
    val inputRead = MutableLiveData<String>()

    val inputFind = MutableLiveData<String>()

    val editInputAuthor = MutableLiveData<String>()
    val editInputTitle = MutableLiveData<String>()
    val editInputComment = MutableLiveData<String>()
    val editInputRead = MutableLiveData<String>()

    fun save(){
        val author = inputAuthor.value!!
        val title = inputTitle.value!!
        val comment = inputComment.value!!
        val read = inputRead.value!!
        insertBook(Book(0, author, title, comment, read))
        inputAuthor.value = ""
        inputTitle.value = ""
        inputComment.value = ""
        inputRead.value = ""
    }

    fun edit(book:Book){
        val author =   editInputAuthor.value!!
        val title = editInputTitle.value!!
        val comment = editInputComment.value!!
        val read = editInputRead.value!!
        updateBook(Book(book.id, author, title, comment, read))
        editInputAuthor.value = ""
        editInputTitle.value = ""
        editInputComment.value = ""
        editInputRead.value = ""
    }

    fun deleteAll(){
        clearAll()
    }

    fun delete(book: Book){
        deleteBook(book)
    }

    fun addScanned(book: Book){
        insertBook(book)
    }

    private fun insertBook(book: Book)=viewModelScope.launch {
       repository.insert(book)
    }

    private fun updateBook(book: Book) = viewModelScope.launch {
        repository.update(book)
    }

    private fun deleteBook(book: Book) = viewModelScope.launch {
        repository.delete(book)
    }

    private fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun getSavedBooks() = liveData {
        repository.books.collect {
            emit(it)
        }
    }
}