package com.qoiu.translator.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
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
import com.qoiu.utils.viewById
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.scope.currentScope

private const val HISTORY_ACTIVITY_PATH = "com.qoiu.historyscreen.history.HistoryActivity"
private const val HISTORY_ACTIVITY_FEATURE_NAME = "historyScreen"
private const val REQUEST_CODE = 42


class MainActivity : BaseActivity<AppState, MainInteractor>() {

    override val layoutRes: Int = R.layout.activity_main
    private lateinit var splitInstallManager: SplitInstallManager
    private lateinit var appUpdateManager: AppUpdateManager


    private val editText by viewById<TextInputEditText>(R.id.input_word)
    private val btnSearch by viewById<Button>(R.id.search_btn)

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
                        data.text,
                        convertMeaningsToString(data.meanings),
                        data.meanings[0].imageUrl,
                        data.meanings[0].transcription

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

        btnSearch.setOnClickListener {
            model.getData(editText.text.toString(), true)
        }
        initViewModel()
        initViews()
        checkForUpdates()
    }

    private fun initViewModel() {
        check(recycler_view.adapter == null){
            "The ViewModel should be installed first"
        }
        injectDependencies()
        val viewModel: MainViewModel by currentScope.inject()
        model=viewModel
        if (recycler_view.adapter != null) {
            throw IllegalStateException("View model not installed")
        }
        model.subscribe().observe(this@MainActivity, Observer<AppState> { renderData(it) })
    }

    private fun initViews() {
        recycler_view.layoutManager = LinearLayoutManager(baseContext)
        recycler_view.adapter = adapter
    }

    override fun setDataToAdapter(data: List<SearchResults>) {
        adapter.setData(data)
    }

    private val stateUpdatedListener: InstallStateUpdatedListener = object : InstallStateUpdatedListener {
        override fun onStateUpdate(state: InstallState?) {
            state?.let {
                if (it.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }
            }
        }
    }

    private fun popupSnackbarForCompleteUpdate() {
        Snackbar.make(
            findViewById(R.id.activity_layout),
            "An update has just been downloaded.",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("RESTART") { appUpdateManager.completeUpdate() }
            show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                appUpdateManager.unregisterListener(stateUpdatedListener)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Update flow failed! Result code: $resultCode",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }
                if (appUpdateInfo.updateAvailability()
                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                ) {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        IMMEDIATE,
                        this,
                        REQUEST_CODE
                    )
                }
            }
    }
    private fun checkForUpdates(){
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        val appUpdateInfo = appUpdateManager.appUpdateInfo
        appUpdateInfo.addOnSuccessListener { appUpdateIntent ->
            if(
                appUpdateIntent.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateIntent.isUpdateTypeAllowed(IMMEDIATE)
            ){
                appUpdateManager.registerListener(stateUpdatedListener)
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateIntent,
                    IMMEDIATE,
                    this,
                    REQUEST_CODE
                )
            }
        }
    }
}