package com.qoiu.translator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qoiu.translator.presentor.AppState
import com.qoiu.translator.presentor.View

class MainActivity : BaseActivity<AppState>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun renderData(appState: AppState) {
        TODO("Not yet implemented")
    }

    override fun renderData(dataModel: DataModel) {
        TODO("Not yet implemented")
    }
}