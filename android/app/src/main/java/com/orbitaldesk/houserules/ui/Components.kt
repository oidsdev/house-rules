package com.orbitaldesk.houserules.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orbitaldesk.houserules.data.Block
import com.orbitaldesk.houserules.data.CheckItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HrScaffold(
    title: String,
    showBack: Boolean = false,
    onBack: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    if (showBack) {
                        TextButton(onClick = onBack) {
                            Text("‹ Back", fontWeight = FontWeight.Bold)
                        }
                    }
                },
                actions = actions,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding -> content(padding) }
}

/** Small "Checked" / "Not checked" label, mirroring the site's tags. */
@Composable
fun CheckedTag(checked: Boolean) {
    val bg = if (checked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    val fg = if (checked) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
    Text(
        text = if (checked) "Checked" else "Not checked",
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = fg,
        modifier = Modifier
            .background(bg, RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    )
}

/** A single checklist row with a real checkbox the user can tick. */
@Composable
fun CheckRow(
    item: CheckItem,
    isChecked: Boolean,
    onToggle: (Boolean) -> Unit
) {
    val uriHandler = LocalUriHandler.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle(!isChecked) }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onToggle,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                checkmarkColor = MaterialTheme.colorScheme.onPrimary
            )
        )
        Spacer(Modifier.width(8.dp))
        Column(Modifier.weight(1f)) {
            Text(item.text, fontSize = 15.sp, lineHeight = 21.sp)
            Spacer(Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                CheckedTag(item.checked)
                if (item.source != null) {
                    Spacer(Modifier.width(8.dp))
                    TextButton(
                        onClick = { uriHandler.openUri(item.source.url) },
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("(source)", fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

/** Renders one content Block (used by paste guides, guides, about). */
@Composable
fun BlockRenderer(block: Block) {
    val uriHandler = LocalUriHandler.current
    when (block) {
        is Block.Heading -> {
            Spacer(Modifier.height(16.dp))
            Text(block.text, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.height(8.dp))
        }
        is Block.Paragraph -> {
            Text(block.text, fontSize = 15.sp, lineHeight = 22.sp)
            Spacer(Modifier.height(10.dp))
        }
        is Block.NumberedList -> {
            block.items.forEachIndexed { i, item ->
                Row(Modifier.padding(vertical = 4.dp)) {
                    Text("${i + 1}. ", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text(item, fontSize = 15.sp, lineHeight = 22.sp)
                }
            }
            Spacer(Modifier.height(10.dp))
        }
        is Block.CheckList -> {
            // Static (non-interactive) checklist inside guides.
            block.items.forEach { item ->
                Row(Modifier.padding(vertical = 6.dp), verticalAlignment = Alignment.Top) {
                    Text("• ", fontSize = 15.sp)
                    Column(Modifier.weight(1f)) {
                        Text(item.text, fontSize = 15.sp, lineHeight = 21.sp)
                        Spacer(Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CheckedTag(item.checked)
                            if (item.source != null) {
                                Spacer(Modifier.width(8.dp))
                                TextButton(
                                    onClick = { uriHandler.openUri(item.source.url) },
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Text("(source)", fontSize = 13.sp)
                                }
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
        }
        is Block.Example -> {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    block.text,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Spacer(Modifier.height(10.dp))
        }
        is Block.SourceLink -> {
            TextButton(
                onClick = { uriHandler.openUri(block.url) },
                contentPadding = PaddingValues(vertical = 2.dp)
            ) {
                Text("Source: ${block.name}", fontSize = 13.sp)
            }
            Spacer(Modifier.height(6.dp))
        }
        is Block.Note -> {
            Text(
                block.text,
                fontSize = 13.sp,
                lineHeight = 19.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(10.dp))
        }
    }
}
