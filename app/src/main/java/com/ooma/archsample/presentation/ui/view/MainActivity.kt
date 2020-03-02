package com.ooma.archsample.presentation.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ooma.archsample.Navigator
import com.ooma.archsample.R

class MainActivity : AppCompatActivity() {

    val navigator: Navigator = Navigator(supportFragmentManager)

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
