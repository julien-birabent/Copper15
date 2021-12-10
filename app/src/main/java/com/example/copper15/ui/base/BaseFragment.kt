package com.example.copper15.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.copper15.viewmodel.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<LayoutBinding : ViewDataBinding, VM : ViewModel> : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected lateinit var viewModel: VM
    abstract var viewModelBindingVariable: Int
    protected abstract var layoutResId: Int

    protected lateinit var layoutBinding: LayoutBinding

    abstract fun provideViewModel(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layoutBinding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return layoutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDataBindingVariables(layoutBinding)
        layoutBinding.lifecycleOwner = this
        layoutBinding.executePendingBindings()
    }

    protected open fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(viewModelBindingVariable, viewModel)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun performDependencyInjection() = run { AndroidSupportInjection.inject(this) }

    fun Fragment.displayNavigateUpButton() {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun Fragment.hideNavigateUpButton() {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    fun Fragment.actionBarTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }

}