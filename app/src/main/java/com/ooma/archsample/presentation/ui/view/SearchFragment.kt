package com.ooma.archsample.presentation.ui.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ooma.archsample.R
import com.ooma.archsample.domain.model.SearchUserSuggestion
import com.ooma.archsample.extension.failure
import com.ooma.archsample.extension.loading
import com.ooma.archsample.extension.success
import com.ooma.archsample.extension.viewModel
import com.ooma.archsample.presentation.ui.rv.adapter.SearchAdapter
import com.ooma.archsample.presentation.ui.rv.adapter.SearchUserClickListener
import com.ooma.archsample.presentation.ui.utils.BaseTextWatcher
import com.ooma.archsample.presentation.viewmodel.SearchViewModel
import com.ooma.archsample.presentation.viewmodel.factory.SearchViewModelFactory
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(),
    SearchUserClickListener {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val adapter = SearchAdapter(this)
    private lateinit var viewModel: SearchViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val factory = SearchViewModelFactory((activity as MainActivity).navigator)
        viewModel = viewModel(factory) {
            success(searchSuggestions, ::renderUserSuggestions)
            failure(failure, ::renderFailure)
            loading(loading, ::renderLoading)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search_users_recycler_view.adapter = adapter

        viewModel.observeSearchView()
        search_users_query_edit_text.addTextChangedListener(object : BaseTextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.setSearchText(s.toString())
            }
        })
    }

    override fun onSuggestionClick(suggestion: SearchUserSuggestion) {
        viewModel.onUserSuggestionClick(suggestion)
    }

    private fun renderLoading(unit: Unit) {
        search_users_error_text_view.visibility = View.GONE
        search_users_recycler_view.visibility = View.GONE
        search_users_progress_bar.visibility = View.VISIBLE
    }

    private fun renderUserSuggestions(users: List<SearchUserSuggestion>) {
        search_users_progress_bar.visibility = View.GONE
        search_users_error_text_view.visibility = View.GONE
        search_users_recycler_view.visibility = View.VISIBLE

        //todo deal with seeing-previous-list-for-a-moment issue
        search_users_recycler_view.adapter = SearchAdapter(this)
        (search_users_recycler_view.adapter as SearchAdapter).submitList(users)
        //adapter.submitList(users)
    }

    private fun renderFailure(throwable: Throwable) {
        search_users_progress_bar.visibility = View.GONE
        search_users_recycler_view.visibility = View.GONE
        search_users_error_text_view.visibility = View.VISIBLE

        search_users_error_text_view.text = "Error occurred: ${throwable.message}"
    }
}