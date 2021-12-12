package com.example.copper15.ui.screen.offers

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
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
import com.example.copper15.ui.item.OfferListItem
import com.example.copper15.ui.screen.BaseFragment
import com.google.android.material.snackbar.Snackbar





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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSortingButtons()
        setupSwipeRefreshLayout()
    }

    override fun onStart() {
        super.onStart()

        viewModel.allOffers.observe(owner = viewLifecycleOwner) { results ->
            when(results){
                is ResultState.Error -> showErrorSnackBar()
                is ResultState.Success -> {
                    results.data.let { offersList ->
                        adapter.updateList(createAdapterList(offersList))
                    }
                }
                else -> {}
            }
        }
    }

    private fun showErrorSnackBar(){
        Snackbar.make(layoutBinding.root, getText(R.string.snackbar_error_message), Snackbar.LENGTH_INDEFINITE).apply {
            setAction(R.string.snackbar_retry_action){
                viewModel.retryToFetchOffers()
                this.dismiss()
            }
        }.show()
    }

    private fun setupSortingButtons() {
        layoutBinding.run {
            offerListSortingByNameButton.setOnCheckedChangeListener { _, isChecked ->
                this@OfferListFragment.viewModel.sortOfferByName(isChecked)
            }
            offerListSortingByCashButton.setOnCheckedChangeListener { _, isChecked ->
                this@OfferListFragment.viewModel.sortOfferByCashBack(isChecked)
            }

            offerListSortingByNameButton.isChecked = false
            offerListSortingByCashButton.isChecked = false
        }
    }

    private fun setupSwipeRefreshLayout(){
        layoutBinding.offerList.componentListSwipeRefreshContainer.setOnRefreshListener {
            viewModel.retryToFetchOffers()
            layoutBinding.offerList.componentListSwipeRefreshContainer.isRefreshing
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