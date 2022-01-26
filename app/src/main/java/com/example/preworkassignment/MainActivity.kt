package com.example.preworkassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.preworkassignment.R.*
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter // we are going to finally assign this variable but later
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // remove the item from the list
                listOfTasks.removeAt(position)
                // notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }

       loadItems() // poplate

        // look up recycleView in the layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)

        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter

        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        //set up the button and the input field so that the user can enter a task and add it to the list
        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        // get a reference to the button
        // and then set an onClick listener to it
        findViewById<Button>(R.id.button).setOnClickListener {
            // grab the text the user has inputted into @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()

            // add the string to our list of task: listOfTasks
            listOfTasks.add(userInputtedTask)

            // notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)

            // reset text field
            inputTextField.setText("")

            saveItems()
        }

    }

    // save the data that user has inputted
// save data by writing and reading from a file
    // get the file we need
    fun getDataFile(): File {
        // every line will represent a task in our list of tasks

        return File(filesDir, "data.txt")
    }

    // create a method to get the file that we need

    // load the item by reading every line in our file
    fun loadItems() {
        try {
            listOfTasks =
                org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    // save items by writing them to a file
    fun saveItems() {
        try {
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }




}