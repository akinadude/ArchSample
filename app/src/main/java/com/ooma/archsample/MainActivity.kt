package com.ooma.archsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ooma.archsample.presentation.view.ProfileFragment

class MainActivity : AppCompatActivity() {

    val navigator: Navigator = Navigator(supportFragmentManager, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            navigator.openStartScreen()
        }
    }

    override fun onBackPressed() {
        if (!navigator.onBackPressed())
            super.onBackPressed()
    }
}
