package com.example.copper15.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.copper15.viewmodel.ViewModelFactory
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseActivity<LayoutBinding : ViewDataBinding, VM : ViewModel> :
    AppCompatActivity(), HasAndroidInjector {

    abstract var layoutId: Int
    abstract var viewModelClass: Class<VM>
    abstract var viewModelBindingVariable: Int

    protected lateinit var viewModel: VM
    protected lateinit var layoutBinding: LayoutBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var fragmentAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = fragmentAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        performDataBinding()

    }

    private fun performDataBinding() {
        layoutBinding = DataBindingUtil.setContentView(this, layoutId)
        layoutBinding.lifecycleOwner = this

        viewModel = ViewModelProvider(this, viewModelFactory).get(viewModelClass)
        /* In the xml, bind the viewModel variable to the activity layout's binding */
        setDataBindingVariables(layoutBinding)
        layoutBinding.executePendingBindings()
    }

    private fun performDependencyInjection() {
        AndroidInjection.inject(this)
    }

    /**
     * Override this method when another variable needs to be bind.
     * Be sure to call super().
     */
    protected fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(viewModelBindingVariable, viewModel)
    }
}