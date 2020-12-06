package com.qoiu.translator.mvp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qoiu.translator.mvp.model.data.DataModel
import com.qoiu.translator.mvp.presenter.Presenter
import com.qoiu.translator.mvp.presenter.View

abstract class BaseActivity <T : DataModel> : AppCompatActivity(), View {
        protected lateinit var presenter: Presenter<T, View>

        protected abstract fun createPresenter(): Presenter<T, View>

        abstract override fun renderData(dataModel: DataModel)

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            presenter = createPresenter()
        }

        override fun onStart() {
            super.onStart()
            presenter.attachView(this)
        }

        override fun onStop() {
            super.onStop()
            presenter.detachView(this)
        }
}