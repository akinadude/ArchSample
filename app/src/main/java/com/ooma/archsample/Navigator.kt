package com.ooma.archsample

import androidx.fragment.app.FragmentManager
import com.ooma.archsample.domain.model.SearchUserSuggestion
import com.ooma.archsample.presentation.ui.view.ProfileFragment
import com.ooma.archsample.presentation.ui.view.SearchFragment
import com.ooma.archsample.presentation.ui.view.StartFragment

class Navigator(private val fragmentManager: FragmentManager) {

    fun openStartScreen() {
        fragmentManager.beginTransaction()
            .replace(R.id.container, StartFragment.newInstance())
            .commit()
    }

    fun openUsersSearchScreen() {
        fragmentManager.beginTransaction()
            .replace(R.id.container, SearchFragment.newInstance())
            .addToBackStack("SearchFragment")
            .commit()
    }

    fun openUserProfileScreen(suggestion: SearchUserSuggestion) {
        fragmentManager.beginTransaction()
            .replace(R.id.container, ProfileFragment.newInstance(suggestion))
            .addToBackStack("UserProfileFragment")
            .commit()
    }

    fun onBackPressed(): Boolean {
        val count = fragmentManager.backStackEntryCount
        return if (count == 0) {
            false
        } else {
            fragmentManager.popBackStack()
            true
        }
    }
}