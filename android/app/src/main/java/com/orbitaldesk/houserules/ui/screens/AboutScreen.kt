package com.orbitaldesk.houserules.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orbitaldesk.houserules.data.ABOUT_BLOCKS
import com.orbitaldesk.houserules.data.ABOUT_TITLE
import com.orbitaldesk.houserules.ui.BlockRenderer
import com.orbitaldesk.houserules.ui.HrScaffold

@Composable
fun AboutScreen(onBack: () -> Unit) {
    HrScaffold(title = "House Rules", showBack = true, onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(20.dp)
        ) {
            Text(ABOUT_TITLE, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            ABOUT_BLOCKS.forEach { BlockRenderer(it) }
            Spacer(Modifier.height(24.dp))
        }
    }
}
