package com.qoiu.translator.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.qoiu.translator.R
import com.qoiu.model.AppState
import com.qoiu.core.BaseActivity
import com.qoiu.model.SearchResults
import com.qoiu.translator.convertMeaningsToString
import com.qoiu.translator.injectDependencies
import com.qoiu.translator.view.DescriptionActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val HISTORY_ACTIVITY_PATH = "com.qoiu.historyscreen.history.HistoryActivity"
private const val HISTORY_ACTIVITY_FEATURE_NAME = "historyScreen"


class MainActivity : BaseActivity<AppState, MainInteractor>() {


    private lateinit var splitInstallManager: SplitInstallManager

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
                        data.meanings!![0].imageUrl,
                        data.meanings!![0].transcription

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
                //startActivity(Intent(this, HistoryActivity::class.java))
                splitInstallManager = SplitInstallManagerFactory.create(applicationContext)
                val request = SplitInstallRequest
                    .newBuilder()
                    .addModule(HISTORY_ACTIVITY_FEATURE_NAME)
                    .build()
                splitInstallManager
                    .startInstall(request)
                    .addOnSuccessListener {
                        val intent = Intent().setClassName(packageName, HISTORY_ACTIVITY_PATH)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            applicationContext,
                            "Couldn't download feature: ${it.message}",
                            Toast.LENGTH_LONG)
                            .show()
                    }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<TextInputEditText>(R.id.input_word)
        val btnSearch = findViewById<Button>(R.id.search_btn)
        btnSearch.setOnClickListener {
            model.getData(editText.text.toString(), true)
        }
        initViewModel()
        initViews()
    }

    private fun initViewModel() {
        check(recycler_view.adapter == null){
            "The ViewModel should be installed first"
        }
        injectDependencies()
        if (recycler_view.adapter != null) {
            throw IllegalStateException("View model not installed")
        }
        val viewModel : MainViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this@MainActivity, Observer<AppState> { renderData(it) })
    }

    private fun initViews() {
        recycler_view.layoutManager = LinearLayoutManager(baseContext)
        recycler_view.adapter = adapter
    }

    override fun setDataToAdapter(data: List<SearchResults>) {
        adapter.setData(data)
    }
}