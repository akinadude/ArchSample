package com.ooma.archsample.presentation.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ooma.archsample.R
import com.ooma.archsample.data.model.User
import com.ooma.archsample.domain.model.SearchUserSuggestion
import com.ooma.archsample.extension.failure
import com.ooma.archsample.extension.loading
import com.ooma.archsample.extension.success
import com.ooma.archsample.extension.viewModel
import com.ooma.archsample.presentation.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    companion object {
        //todo naming style
        private const val bundle_user_login = "ARG_SKIP_BUTTON_TITLE_RES_ID"

        fun newInstance() = ProfileFragment()

        fun newInstance(suggestion: SearchUserSuggestion): ProfileFragment {
            val f = ProfileFragment()
            f.arguments = Bundle().apply {
                putString(bundle_user_login, suggestion.login)
            }
            return f
        }
    }

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel = viewModel {
            success(profile, ::renderUserProfile)//todo Create composite source of a model
            failure(failure, ::renderFailure)
            loading(loading, ::renderLoading)
        }

        val login = arguments?.getString(bundle_user_login) ?: "JohnnySC"
        profileViewModel.getUserProfile(login)
    }

    private fun renderLoading(unit: Unit) {
        profile_username_text_view.visibility = View.GONE
        profile_progress_bar.visibility = View.VISIBLE
    }

    private fun renderUserProfile(profile: User) {
        profile_progress_bar.visibility = View.GONE
        profile_username_text_view.visibility = View.VISIBLE
        profile_username_text_view.text = profile.login
    }

    private fun renderFailure(throwable: Throwable) {
        profile_progress_bar.visibility = View.GONE
        profile_username_text_view.visibility = View.VISIBLE
        profile_username_text_view.text = "Error occurred: ${throwable.message}"
    }
}