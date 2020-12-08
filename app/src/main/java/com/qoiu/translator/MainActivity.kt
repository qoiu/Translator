package com.qoiu.translator

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.qoiu.translator.mvp.model.MainInteractor
import com.qoiu.translator.mvp.model.data.AppState
import com.qoiu.translator.mvp.view.BaseActivity
import com.qoiu.translator.mvp.view.MainViewRecycler
import com.qoiu.translator.mvvm.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<AppState, MainInteractor>() {
    private val adapter: MainViewRecycler? = null

    private  val observer = Observer<AppState>{renderData(it)}
    override val model: MainViewModel by lazy {
        //ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText=findViewById<TextInputEditText>(R.id.input_word)
        val btnSearch=findViewById<Button>(R.id.search_btn)
        btnSearch.setOnClickListener {
            //presenter.getData(editText.text.toString(),true)
            model.getData(editText.text.toString(), true).observe(this@MainActivity, observer)
        }

        model.viewState.observe(this,observer)

    }

    override fun renderData(appState: AppState) {
        when(appState){
            is AppState.Success->{
                val searchResult = appState.data
                if(searchResult.isEmpty()){
                   hideView()
                } else {
                    showView(main_layout_results)
                    if (adapter == null) {
                        recycler_view.layoutManager= LinearLayoutManager(baseContext)
                        recycler_view.adapter=MainViewRecycler(searchResult)
                    } else {
                        adapter.setData(searchResult)
                    }
                }
            }
            is AppState.Loading -> {
                showView(main_layout_progress)
                if(appState.progress != null){
                    progressBar.progress=appState.progress
                } else{
                   hideView()
                }
            }
            is AppState.Error->{
                showView(main_layout_error)
                textview_error.text=appState.error.message
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