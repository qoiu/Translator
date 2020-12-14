package com.qoiu.translator.mvp.view

import androidx.appcompat.app.AppCompatActivity
import com.qoiu.translator.mvp.model.data.AppState
import com.qoiu.translator.Interactor
import com.qoiu.translator.mvvm.viewmodel.BaseViewModel

abstract class BaseActivity <T : AppState, I : Interactor<T>> : AppCompatActivity() {

        abstract val model: BaseViewModel<T>

        protected var isNetworkAble=false

        abstract fun renderData(appState: AppState)

        companion object {
                private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"
        }
}