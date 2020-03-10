package com.ooma.archsample.presentation.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import com.ooma.archsample.R
import com.ooma.archsample.presentation.viewmodel.StartViewModel
import com.ooma.archsample.presentation.viewmodel.StartViewModelFactory
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : Fragment() {

    companion object {
        fun newInstance() = StartFragment()
    }

    private val startViewModel: StartViewModel by lazy {
        val factory = StartViewModelFactory((activity as MainActivity).navigator)
        ViewModelProviders.of(this, factory).get(StartViewModel::class.java)
    }

    //todo what about passing params?
    val viewModel by viewModels<StartViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_start, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //startViewModel.setNavigator((activity as MainActivity).navigator)
        start_button.setOnClickListener { startViewModel.onStartClick() }
    }
}