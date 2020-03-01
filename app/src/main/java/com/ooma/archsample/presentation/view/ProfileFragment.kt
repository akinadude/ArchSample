package com.ooma.archsample.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ooma.archsample.R
import com.ooma.archsample.data.model.UserProfile
import com.ooma.archsample.extension.failure
import com.ooma.archsample.extension.observe
import com.ooma.archsample.extension.viewModel
import com.ooma.archsample.presentation.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profile_username.text = "This is a username"

        profileViewModel = viewModel {
            observe(profile, ::renderUserProfile)
            failure(failure, ::renderFailure)
        }
        profileViewModel.getUserProfile("akinadude")//JohnnySC

    }

    private fun renderUserProfile(profile: UserProfile) {
        profile_username.text = profile.login
    }

    private fun renderFailure(throwable: Throwable) {
        profile_username.text = "Error occurred: ${throwable.message}"
    }
}