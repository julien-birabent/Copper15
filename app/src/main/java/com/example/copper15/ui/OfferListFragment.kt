package com.example.copper15.ui

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.example.copper15.BR
import com.example.copper15.R
import com.example.copper15.data.repository.ResultState
import com.example.copper15.data.repository.data
import com.example.copper15.databinding.FragmentListOfferBinding
import com.example.copper15.domain.model.Offer
import com.example.copper15.domain.viewmodel.ListOfferViewModel
import com.example.copper15.ui.adapter.DataBindingGenericAdapter
import com.example.copper15.ui.adapter.ItemSelectionCallback
import com.example.copper15.ui.adapter.ViewTypeHolder
import com.example.copper15.ui.base.BaseFragment
import com.example.copper15.ui.item.OfferListItem


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

        viewModel.allOffers.observe(viewLifecycleOwner) { results ->
            if (results !is ResultState.Error) {
                results.data?.let { offersList ->
                    adapter.updateList(createAdapterList(offersList))
                }
            }
        }
    }

    private fun createAdapterList(offers: List<Offer>): List<ViewTypeHolder<OfferListItem<Offer>, ItemSelectionCallback<*>>> {
        return offers.map { createItemViewTypeHolder(it) }
    }

    private fun createItemViewTypeHolder(item: Offer): ViewTypeHolder<OfferListItem<Offer>, ItemSelectionCallback<*>> {
        return ViewTypeHolder(
            viewData = OfferListItem(item.name, item.imageUrl, item.cashBack.toString(), item),
            layoutResId = R.layout.item_offer_card
        )
    }

}