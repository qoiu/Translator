package com.qoiu.translator.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.qoiu.translator.R
import com.qoiu.translator.convertMeaningsToString
import com.qoiu.translator.view.history.HistoryActivity
import com.qoiu.translator.mvp.model.data.AppState
import com.qoiu.translator.mvp.model.data.SearchResults
import com.qoiu.translator.view.BaseActivity
import com.qoiu.translator.view.DescriptionActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.get

class MainActivity : BaseActivity<AppState, MainInteractor>() {

    private val viewModel: MainViewModel = get()

    private val adapter: MainViewRecycler by lazy {
        MainViewRecycler(
            onListItemClickListener
        )
    }

    override lateinit var model: MainViewModel

    private val onListItemClickListener:
            MainViewRecycler.OnListItemClickListener =
        object : MainViewRecycler.OnListItemClickListener {
            override fun onItemClick(data: SearchResults) {
                startActivity(
                    DescriptionActivity.getIntent(
                        this@MainActivity,
                        data.text!!,
                        convertMeaningsToString(data.meanings!!),
                        data.meanings[0].imageUrl
                    )
                )
            }
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText=findViewById<TextInputEditText>(R.id.input_word)
        val btnSearch=findViewById<Button>(R.id.search_btn)
        btnSearch.setOnClickListener {
            model.getData(editText.text.toString(), true)
        }
        initViewModel()
        initViews()
    }

    private fun initViewModel(){
        if(recycler_view.adapter !=null){
            throw IllegalStateException("View model not installed")
        }
        model = viewModel
        model.subscribe().observe(this@MainActivity, Observer<AppState> { renderData(it) })
    }

    private fun initViews(){
        recycler_view.layoutManager = LinearLayoutManager(baseContext)
        recycler_view.adapter = adapter
    }

    override fun setDataToAdapter(data: List<SearchResults>) {
        adapter.setData(data)
    }
}