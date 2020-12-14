package com.qoiu.translator

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.qoiu.translator.mvp.model.MainInteractor
import com.qoiu.translator.mvp.model.data.AppState
import com.qoiu.translator.mvp.view.BaseActivity
import com.qoiu.translator.mvp.view.MainViewRecycler
import com.qoiu.translator.mvvm.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.get
import java.lang.IllegalStateException

class MainActivity : BaseActivity<AppState, MainInteractor>() {

    private val viewModel: MainViewModel = get()

    private val adapter: MainViewRecycler by lazy { MainViewRecycler(arrayListOf())}

    override lateinit var model: MainViewModel


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



    override fun renderData(appState: AppState) {
        when(appState) {
            is AppState.Success -> {
                val searchResult = appState.data
                if (searchResult.isNullOrEmpty()) {
                    hideView()
                    Toast.makeText(this,getString(R.string.empty_server_response_on_success),Toast.LENGTH_LONG).show()
                } else {
                        adapter.setData(searchResult)
                        showView(main_layout_results)
                }
            }
            is AppState.Loading -> {
                showView(main_layout_progress)
                if (appState.progress != null) {
                    progressBar.progress = appState.progress
                } else {
                    hideView()
                }
            }
            is AppState.Error -> {
                showView(main_layout_error)
                textview_error.text = appState.error.message
            }
        }
    }

    private fun showView(view: LinearLayout){
        hideView()
        view.visibility= VISIBLE
    }

    private fun hideView(){
        main_layout_error.visibility=GONE
        main_layout_progress.visibility=GONE
        main_layout_results.visibility=GONE
    }
}