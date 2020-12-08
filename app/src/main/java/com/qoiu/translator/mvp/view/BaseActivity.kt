package com.qoiu.translator.mvp.view

import androidx.appcompat.app.AppCompatActivity
import com.qoiu.translator.mvp.model.data.AppState
import com.qoiu.translator.mvp.presenter.Interactor
import com.qoiu.translator.mvvm.viewmodel.BaseViewModel

abstract class BaseActivity <T : AppState, I : Interactor<T>> : AppCompatActivity() {

        abstract val model: BaseViewModel<T>

        abstract fun renderData(appState: AppState)


}