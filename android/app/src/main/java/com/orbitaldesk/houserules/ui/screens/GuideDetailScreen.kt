package com.orbitaldesk.houserules.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orbitaldesk.houserules.data.GUIDES
import com.orbitaldesk.houserules.ui.BlockRenderer
import com.orbitaldesk.houserules.ui.HrScaffold

@Composable
fun GuideDetailScreen(
    guideId: String,
    onBack: () -> Unit
) {
    val guide = GUIDES.first { it.id == guideId }
    HrScaffold(title = "Short guides", showBack = true, onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(20.dp)
        ) {
            Text(
                guide.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 30.sp
            )
            Spacer(Modifier.height(12.dp))
            Text(
                guide.intro,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(8.dp))
            guide.blocks.forEach { BlockRenderer(it) }
            Spacer(Modifier.height(24.dp))
        }
    }
}
