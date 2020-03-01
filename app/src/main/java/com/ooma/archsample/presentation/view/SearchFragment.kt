package com.ooma.archsample.presentation.view

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ooma.archsample.R
import com.ooma.archsample.data.model.UserProfile
import com.ooma.archsample.extension.failure
import com.ooma.archsample.extension.observe
import com.ooma.archsample.extension.viewModel
import com.ooma.archsample.presentation.ui.BaseTextWatcher
import com.ooma.archsample.presentation.ui.rv.adapter.SearchAdapter
import com.ooma.archsample.presentation.viewmodel.SearchViewModel
import com.ooma.archsample.presentation.viewmodel.StartViewModel
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    private val adapter = SearchAdapter()

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
            observe(searchUsers, ::renderSearchUsers)
            failure(failure, ::renderFailure)
        }

        //todo Create SearchUsers use case and use it within viewModel.subscribeToSubject

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

    private fun renderSearchUsers(users: List<UserProfile>) {
        adapter.submitList(users)
    }

    private fun renderFailure(throwable: Throwable) {
        search_users_error_text_view.text = "Error occurred: ${throwable.message}"
    }
}