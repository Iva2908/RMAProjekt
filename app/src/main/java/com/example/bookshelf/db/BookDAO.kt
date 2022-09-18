package com.example.bookshelf.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDAO {
    @Insert
    suspend fun insertBook(book: Book):Long
    @Update
    suspend fun updateBook(book: Book):Int
    @Delete
    suspend fun deleteBook(book: Book):Int
    @Query("DELETE FROM book_data_table")
    suspend fun deleteAll():Int
    @Query("SELECT * FROM book_data_table")
    fun getAllBooks():Flow<List<Book>>
}