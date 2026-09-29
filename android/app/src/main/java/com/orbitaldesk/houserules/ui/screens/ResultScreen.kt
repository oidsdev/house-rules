package com.orbitaldesk.houserules.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orbitaldesk.houserules.data.GUIDES
import com.orbitaldesk.houserules.data.PASTE_GUIDES
import com.orbitaldesk.houserules.data.TEST_FOOTNOTE
import com.orbitaldesk.houserules.data.TEST_INTRO
import com.orbitaldesk.houserules.data.TEST_PROMPTS
import com.orbitaldesk.houserules.data.RulesEngine
import com.orbitaldesk.houserules.data.grokChecklist
import com.orbitaldesk.houserules.data.museChecklist
import com.orbitaldesk.houserules.data.otherChecklist
import com.orbitaldesk.houserules.ui.BlockRenderer
import com.orbitaldesk.houserules.ui.CheckRow
import com.orbitaldesk.houserules.ui.HrScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    agent: String,
    answers: Map<String, String>,
    extras: Map<String, String>,
    onOpenGuide: (String) -> Unit,
    onRestart: () -> Unit,
    onBack: () -> Unit
) {
    val rulesText = remember(answers, extras) { RulesEngine.buildRules(answers, extras) }
    val clipboard = LocalClipboardManager.current
    val context = LocalContext.current
    var copied by remember { mutableStateOf(false) }

    var pasteTab by remember(agent) { mutableIntStateOf(tabIndexFor(agent)) }
    val tabKeys = listOf("muse", "grok", "other")
    val tabLabels = listOf("Muse", "Grok Bot", "Other")
    val checklistItems = remember(agent, answers) {
        when (agent) {
            "muse" -> museChecklist(answers["money"])
            "grok" -> grokChecklist()
            else -> otherChecklist()
        }
    }
    val ticked = remember(agent) { mutableStateMapOf<Int, Boolean>() }

    HrScaffold(title = "House Rules", showBack = true, onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(20.dp)
        ) {
            Text("Done", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = 1f,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
            Spacer(Modifier.height(20.dp))

            Text("Your house rules", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(
                "Copy this. Then paste it where your agent keeps its standing instructions. Steps are below.",
                fontSize = 15.sp,
                lineHeight = 22.sp
            )
            Spacer(Modifier.height(16.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    rulesText,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(14.dp)
                )
            }
            Spacer(Modifier.height(16.dp))

            Row(Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        clipboard.setText(AnnotatedString(rulesText))
                        copied = true
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(if (copied) "Copied ✓" else "Copy my rules")
                }
                Spacer(Modifier.width(12.dp))
                OutlinedButton(
                    onClick = {
                        val send = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, rulesText)
                        }
                        context.startActivity(Intent.createChooser(send, "Share house rules"))
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Share")
                }
            }
            if (copied) {
                Spacer(Modifier.height(8.dp))
                Text(
                    "Copied. Now paste it into your agent (steps below).",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.height(24.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("This is guidance, not a guarantee.", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Agents can skip or forget rules. Keep approvals on, read each request before " +
                            "you tap yes, and use the settings below. You are still the one in charge.",
                        fontSize = 14.sp,
                        lineHeight = 21.sp
                    )
                }
            }

            // ---- How to paste it ----
            Spacer(Modifier.height(28.dp))
            Text("How to paste it", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            TabRow(
                selectedTabIndex = pasteTab,
                containerColor = MaterialTheme.colorScheme.background,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[pasteTab]),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                tabLabels.forEachIndexed { i, label ->
                    Tab(
                        selected = pasteTab == i,
                        onClick = { pasteTab = i },
                        text = { Text(label, fontSize = 14.sp) }
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            val guide = PASTE_GUIDES.getValue(tabKeys[pasteTab])
            Text(guide.title, fontWeight = FontWeight.Bold, fontSize = 17.sp)
            Spacer(Modifier.height(8.dp))
            guide.blocks.forEach { BlockRenderer(it) }

            // ---- Test it ----
            Spacer(Modifier.height(20.dp))
            Text("Test it (1 minute)", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(TEST_INTRO, fontSize = 15.sp)
            Spacer(Modifier.height(8.dp))
            TEST_PROMPTS.forEachIndexed { i, (prompt, expected) ->
                Row(Modifier.padding(vertical = 6.dp)) {
                    Text("${i + 1}. ", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Column(Modifier.weight(1f)) {
                        Text(prompt, fontWeight = FontWeight.Medium, fontSize = 15.sp)
                        Spacer(Modifier.height(2.dp))
                        Text(
                            expected,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(
                TEST_FOOTNOTE,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 19.sp
            )

            // ---- Settings checklist ----
            Spacer(Modifier.height(28.dp))
            Text("Settings checklist", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(
                "Checked means we found it on the company's own help pages (link included). " +
                    "Not checked means good advice we could not confirm for your app. Menus change, " +
                    "so names may differ.",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 19.sp
            )
            Spacer(Modifier.height(8.dp))
            checklistItems.forEachIndexed { i, item ->
                CheckRow(
                    item = item,
                    isChecked = ticked[i] == true,
                    onToggle = { ticked[i] = it }
                )
            }

            // ---- Short guides ----
            Spacer(Modifier.height(28.dp))
            Text("Short guides", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            GUIDES.forEach { g ->
                GuideRow(title = g.title, onClick = { onOpenGuide(g.id) })
                Spacer(Modifier.height(8.dp))
            }

            // ---- Start over ----
            Spacer(Modifier.height(28.dp))
            OutlinedButton(
                onClick = onRestart,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                Text("Start over")
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

private fun tabIndexFor(agent: String): Int = when (agent) {
    "muse" -> 0
    "grok" -> 1
    else -> 2
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GuideRow(title: String, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, fontSize = 15.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
            Text("›", fontSize = 22.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
