package com.ooma.archsample.presentation.ui.view

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ooma.archsample.R
import com.ooma.archsample.domain.model.SearchUserSuggestion
import com.ooma.archsample.extension.failure
import com.ooma.archsample.extension.observe
import com.ooma.archsample.extension.viewModel
import com.ooma.archsample.presentation.ui.utils.BaseTextWatcher
import com.ooma.archsample.presentation.ui.rv.adapter.SearchUserClickListener
import com.ooma.archsample.presentation.ui.rv.adapter.SearchAdapter
import com.ooma.archsample.presentation.viewmodel.SearchViewModel
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(), SearchUserClickListener {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    private val adapter = SearchAdapter(this)

    /*private val viewModel: SearchViewModel by lazy {
        ViewModelProviders.of(this).get(SearchViewModel::class.java)
    }*/
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search_users_recycler_view.adapter = adapter

        viewModel = viewModel {
            observe(searchUserResult, ::renderUserSuggestions)
            failure(failure, ::renderFailure)
        }
        viewModel.setNavigator((activity as MainActivity).navigator)

        search_query_edit_text.addTextChangedListener(object : BaseTextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.performSearch(s.toString(), publishSubject)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.subscribeToSubject(publishSubject)
    }

    override fun onSuggestionClick(suggestion: SearchUserSuggestion) {
        viewModel.onUserSuggestionClick(suggestion)
    }

    private fun renderUserSuggestions(users: List<SearchUserSuggestion>) {
        adapter.submitList(users)
    }

    private fun renderFailure(throwable: Throwable) {
        search_users_error_text_view.text = "Error occurred: ${throwable.message}"
    }
}