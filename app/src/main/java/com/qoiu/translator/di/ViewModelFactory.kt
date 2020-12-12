package com.qoiu.translator.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModels[modelClass]?.get() as? T
            ?: throw IllegalArgumentException("unknown model class $modelClass")
//        val creator = viewModels[modelClass]
//            ?:viewModels.asIterable().firstOrNull{modelClass.isAssignableFrom(it.key)}?.value
//            ?:throw IllegalArgumentException("unknown model class $modelClass")
//        return try{
//            creator.get() as T
//        }catch (e: Exception){
//            throw RuntimeException(e)
//        }
    }

}