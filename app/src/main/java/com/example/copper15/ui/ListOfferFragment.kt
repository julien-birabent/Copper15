package com.example.copper15.ui

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.example.copper15.BR
import com.example.copper15.R
import com.example.copper15.databinding.FragmentListOfferBinding
import com.example.copper15.ui.adapter.DataBindingGenericAdapter
import com.example.copper15.ui.base.BaseFragment


class ListOfferFragment : BaseFragment<FragmentListOfferBinding, ListOfferViewModel>() {

    override var viewModelBindingVariable: Int = BR.viewModel
    override var layoutResId: Int = R.layout.fragment_list_offer

    override fun provideViewModel(): ListOfferViewModel {
        return ViewModelProvider(this, viewModelFactory)[ListOfferViewModel::class.java]
    }

    private val adapter by lazy { DataBindingGenericAdapter() }

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        super.setDataBindingVariables(binding)
        layoutBinding.setVariable(BR.adapter, adapter)
    }

    override fun onStart() {
        super.onStart()

    }
}