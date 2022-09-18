package com.example.bookshelf.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Book::class], version = 1)
abstract class BookDatabase:RoomDatabase() {
    abstract val bookDAO:BookDAO

    companion object {
        @Volatile
        private var INSTANCE: BookDatabase? = null
        fun getInstance(context:Context): BookDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BookDatabase::class.java,
                        "book_data_table"
                    ).build()
                }
                return instance
            }
        }
    }
}