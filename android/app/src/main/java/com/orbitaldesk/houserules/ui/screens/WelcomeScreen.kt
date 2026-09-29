package com.orbitaldesk.houserules.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orbitaldesk.houserules.data.FOOTER_DISCLAIMER
import com.orbitaldesk.houserules.ui.HrScaffold

@Composable
fun WelcomeScreen(
    onStart: () -> Unit,
    onAbout: () -> Unit
) {
    HrScaffold(title = "House Rules") { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(20.dp)
        ) {
            Text(
                "Give your AI agent house rules.",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp
            )
            Spacer(Modifier.height(16.dp))
            Text(
                "AI agents like Muse and Grok Bot can send messages, buy things, and book stuff " +
                    "for you. They follow instructions. So give them good ones.",
                fontSize = 16.sp,
                lineHeight = 24.sp
            )
            Spacer(Modifier.height(12.dp))
            Text(
                "Answer 10 easy questions. You get a short list of rules to copy and paste into " +
                    "your agent, plus a checklist of settings to change.",
                fontSize = 16.sp,
                lineHeight = 24.sp
            )
            Spacer(Modifier.height(12.dp))
            Text(
                "About 3 minutes. Free. No sign-up. Nothing you pick leaves this device.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(28.dp))
            Button(
                onClick = onStart,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Start", fontSize = 17.sp, modifier = Modifier.padding(vertical = 6.dp))
            }
            Spacer(Modifier.height(24.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Good to know", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Rules help, but they are not a lock. An agent can still get things wrong. " +
                            "That's why you also get a settings checklist. The app's own settings do " +
                            "more to stop actions than any text you paste.",
                        fontSize = 14.sp,
                        lineHeight = 21.sp
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
            TextButton(onClick = onAbout) {
                Text("About and privacy")
            }
            Spacer(Modifier.height(16.dp))
            Text(
                FOOTER_DISCLAIMER,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
