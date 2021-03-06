package com.ooma.archsample.presentation.ui.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ooma.archsample.R
import com.ooma.archsample.presentation.viewmodel.StartViewModel
import com.ooma.archsample.presentation.viewmodel.factory.StartViewModelFactory
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : Fragment() {

    companion object {
        fun newInstance() = StartFragment()
    }

    private lateinit var viewModel: StartViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val factory = StartViewModelFactory((activity as MainActivity).navigator)
        viewModel = ViewModelProvider(this, factory)[StartViewModel::class.java]
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_start, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        start_button.setOnClickListener { viewModel.onStartClick() }
    }
}