package com.ooma.archsample

import android.app.Activity
import androidx.fragment.app.FragmentManager
import com.ooma.archsample.presentation.view.ProfileFragment
import com.ooma.archsample.presentation.view.SearchFragment
import com.ooma.archsample.presentation.view.StartFragment

class Navigator(private val fragmentManager: FragmentManager, private val activity: Activity) {

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

    fun openUserProfileScreen() {
        fragmentManager.beginTransaction()
            .replace(R.id.container, ProfileFragment.newInstance())
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