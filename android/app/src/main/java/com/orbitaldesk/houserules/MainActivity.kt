package com.orbitaldesk.houserules

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.orbitaldesk.houserules.ui.HouseRulesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HouseRulesTheme {
                HouseRulesApp()
            }
        }
    }
}
