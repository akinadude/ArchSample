package com.ooma.archsample.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ooma.archsample.R
import com.ooma.archsample.Result
import com.ooma.archsample.presentation.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProviders.of(this).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.profile_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profile_username.text = "This is a username"

        viewModel.getUserProfile("JohnnySC")//akinadude
        viewModel.profile.observe(viewLifecycleOwner, Observer { result ->
            when(result) {
                is Result.Success -> profile_username.text = result.data.login
                is Result.Failure -> profile_username.text = "Error occurred :("
            }
        })
    }
}