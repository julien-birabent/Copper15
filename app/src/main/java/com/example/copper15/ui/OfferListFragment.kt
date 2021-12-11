package com.example.copper15.ui

import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.example.copper15.BR
import com.example.copper15.R
import com.example.copper15.data.repository.ResultState
import com.example.copper15.data.repository.data
import com.example.copper15.databinding.FragmentListOfferBinding
import com.example.copper15.domain.viewmodel.ListOfferViewModel
import com.example.copper15.ui.adapter.DataBindingGenericAdapter
import com.example.copper15.ui.base.BaseFragment


class OfferListFragment : BaseFragment<FragmentListOfferBinding, ListOfferViewModel>() {

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

        viewModel.allOffers.observe(viewLifecycleOwner){ results ->
            when(results){
                is ResultState.Error -> Log.d("TEST", "Error "+ results.data.toString(), results.throwable)
                is ResultState.Loading -> Log.d("TEST", "Loading "+ results.data.toString())
                is ResultState.Success -> Log.d("TEST", "Success " + results.data.toString())
            }
        }
    }
}