package com.example.bookshelf.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_data_table")
data class Book(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "book_id")
    var id: Int,
    @ColumnInfo(name = "book_author")
    var author: String,
    @ColumnInfo(name = "book_title")
    var title: String,
    @ColumnInfo(name = "book_comment")
    var comment: String,
    @ColumnInfo(name = "book_read")
    var read: String
)