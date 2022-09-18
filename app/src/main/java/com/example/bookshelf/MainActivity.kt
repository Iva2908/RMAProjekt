package com.example.bookshelf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookshelf.databinding.ActivityMainBinding
import com.example.bookshelf.db.Book
import com.example.bookshelf.db.BookDatabase
import com.example.bookshelf.db.BookRepository

class MainActivity : AppCompatActivity() {

    companion object{
        const val RESULT = "RESULT"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var bookViewModel: BookViewModel
    private lateinit var adapter: MyRecyclerViewAdapter
    private var deleteAll = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = BookDatabase.getInstance(application).bookDAO
        val repository = BookRepository(dao)
        val factory = BookViewModelFactory(repository)
        bookViewModel = ViewModelProvider(this,factory).get(BookViewModel::class.java)
        binding.myViewModel = bookViewModel
        binding.lifecycleOwner = this

        initRecyclerView()

        binding.addButton.setOnClickListener {
            Toast.makeText(this@MainActivity, "No field may be empty!", Toast.LENGTH_LONG).show()
            val intent = Intent(applicationContext, AddActivity::class.java)
            startActivity(intent)
        }

        binding.findButton.setOnClickListener {
            if(binding.findInput.text.isEmpty()){
                Toast.makeText(this@MainActivity, "Find field may not be empty!", Toast.LENGTH_LONG).show()
            } else{
                find()
            }

        }

        binding.scanButton.setOnClickListener {
            val intent = Intent(applicationContext, ScanActivity::class.java)
            startActivity(intent)
        }

        bookScanned()


        binding.idButton.setOnClickListener {
            initRecyclerView()
        }

        binding.authorButton.setOnClickListener {
            initSortedByAuthorRecyclerView()
        }

        binding.titleButton.setOnClickListener {
            initSortedByTitleRecyclerView()
        }

    }

    private fun listItemClicked(book: Book){
        Toast.makeText(this@MainActivity, "No field may be empty!", Toast.LENGTH_LONG).show()
        val pass =
                    book.id.toString()+ ", "+
                    book.author.toString() + ", " +
                    book.title.toString() + ", " +
                    book.comment.toString() + ", " +
                    book.read.toString()
        val intent = Intent(applicationContext, EditActivity::class.java)
        intent.putExtra("EDIT", pass)
        startActivity(intent)
    }

    private fun bookScanned(){
        val result = intent.getStringExtra(RESULT)

        if(result != null){
            val parts = result.split(",").toTypedArray()
            bookViewModel.addScanned(Book(
                0,
                parts[0],
                parts[1],
                parts[2],
                parts[3]))
        }
    }

    private fun initRecyclerView(){
        binding.bookRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter({selectedItem: Book ->listItemClicked(selectedItem)})
        binding.bookRecyclerView.adapter = adapter
        displayBooksList()
    }

    private fun initSortedByAuthorRecyclerView(){
        binding.bookRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter({selectedItem: Book ->listItemClicked(selectedItem)})
        binding.bookRecyclerView.adapter = adapter
        displaySortedByAuthorList()
    }

    private fun initSortedByTitleRecyclerView(){
        binding.bookRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter({selectedItem: Book ->listItemClicked(selectedItem)})
        binding.bookRecyclerView.adapter = adapter
        displaySortedByTitleList()
    }

    private fun displayBooksList(){
        bookViewModel.getSavedBooks().observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun displaySortedByAuthorList(){
        bookViewModel.getSavedBooks().observe(this, Observer {
            adapter.sortByAuthorAndSetList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun displaySortedByTitleList(){
        bookViewModel.getSavedBooks().observe(this, Observer {
            adapter.sortByTitleAndSetList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun find(){
        bookViewModel.getSavedBooks().observe(this, Observer {
            var books = it
            var value = bookViewModel.inputFind.value!!
            bookViewModel.inputFind.value = "Found: "

            for(book in books){
                if (book.author == value){
                    bookViewModel.inputFind.value += book.id.toString()
                }
                if(book.title == value){
                    bookViewModel.inputFind.value += book.id.toString()
                }
            }
        })
    }

}