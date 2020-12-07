package com.qoiu.translator

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.qoiu.translator.mvp.view.BaseActivity
import com.qoiu.translator.mvp.model.data.DataModel
import com.qoiu.translator.mvp.presenter.MainPresenter
import com.qoiu.translator.mvp.presenter.Presenter
import com.qoiu.translator.mvp.presenter.View
import com.qoiu.translator.mvp.view.MainViewRecycler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<DataModel>() {
    private val adapter: MainViewRecycler? = null

    override fun createPresenter(): Presenter<DataModel, View> {
        return MainPresenter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText=findViewById<TextInputEditText>(R.id.input_word)
        val btnSearch=findViewById<Button>(R.id.search_btn)
        btnSearch.setOnClickListener {
            presenter.getData(editText.text.toString(),true)
        }

    }

    override fun renderData(dataModel: DataModel) {
        when(dataModel){
            is DataModel.Success->{
                val searchResult = dataModel.data
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
            is DataModel.Loading -> {
                showView(main_layout_progress)
                if(dataModel.progress != null){
                    progressBar.progress=dataModel.progress
                } else{
                   hideView()
                }
            }
            is DataModel.Error->{
                showView(main_layout_error)
                textview_error.text=dataModel.error.message
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