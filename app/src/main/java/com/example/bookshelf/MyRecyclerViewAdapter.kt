package com.example.bookshelf

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bookshelf.databinding.ListItemBinding
import com.example.bookshelf.db.Book

class MyRecyclerViewAdapter(private val clickListener: (Book)-> Unit):
RecyclerView.Adapter<MyViewHolder>(){
    private val booksList = ArrayList<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding:ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(booksList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

    fun setList(books: List<Book>){
        booksList.clear()
        booksList.addAll(books)
    }

    fun sortByAuthorAndSetList(books: List<Book>){
        booksList.clear()
        booksList.addAll(books.sortedBy { it.author })
    }

    fun sortByTitleAndSetList(books: List<Book>){
        booksList.clear()
        booksList.addAll(books.sortedBy { it.title })
    }
}

class MyViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(book: Book, clickListener: (Book) -> Unit){
        binding.idTextView.text = book.id.toString()
        binding.authorTextView.text = book.author
        binding.titleTextView.text = book.title
        binding.commentTextView.text = "Comment: " + book.comment
        binding.readTextView.text = "Read: " + book.read
        binding.listItemLayout.setOnClickListener {
            clickListener(book)
        }
    }
}