package com.orbitaldesk.houserules.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orbitaldesk.houserules.data.QUESTIONS
import com.orbitaldesk.houserules.ui.HrScaffold

@Composable
fun QuizScreen(
    index: Int,
    answers: Map<String, String>,
    extras: Map<String, String>,
    onAnswer: (questionId: String, value: String) -> Unit,
    onExtra: (key: String, value: String) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val q = QUESTIONS[index]
    val selected = answers[q.id]
    val isLast = index == QUESTIONS.size - 1

    HrScaffold(title = "House Rules", showBack = true, onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(20.dp)
        ) {
            Text(
                "Question ${index + 1} of ${QUESTIONS.size}",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = (index + 2) / 12f,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
            Spacer(Modifier.height(20.dp))
            Text(q.title, fontSize = 22.sp, fontWeight = FontWeight.Bold, lineHeight = 28.sp)
            Spacer(Modifier.height(8.dp))
            Text(
                q.hint,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )
            Spacer(Modifier.height(20.dp))

            q.options.forEach { opt ->
                val isSelected = selected == opt.value
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .selectable(
                            selected = isSelected,
                            role = Role.RadioButton,
                            onClick = { onAnswer(q.id, opt.value) }
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant
                        }
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = { onAnswer(q.id, opt.value) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = if (isSelected) {
                                    MaterialTheme.colorScheme.onPrimary
                                } else {
                                    MaterialTheme.colorScheme.primary
                                },
                                unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                        Spacer(Modifier.width(8.dp))
                        Column(Modifier.weight(1f)) {
                            Text(
                                opt.label,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = if (isSelected) {
                                    MaterialTheme.colorScheme.onPrimary
                                } else {
                                    MaterialTheme.colorScheme.onSurface
                                }
                            )
                            if (opt.sub.isNotEmpty()) {
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    opt.sub,
                                    fontSize = 13.sp,
                                    color = if (isSelected) {
                                        MaterialTheme.colorScheme.onPrimary
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    }
                                )
                            }
                        }
                    }
                }

                // Extra input (spending limit / family names) shown when its option is selected.
                if (opt.extra != null && isSelected) {
                    val extra = opt.extra
                    OutlinedTextField(
                        value = extras[extra.key] ?: extra.default,
                        onValueChange = { onExtra(extra.key, it) },
                        label = { Text(extra.label) },
                        singleLine = extra.type == "number",
                        maxLines = if (extra.type == "text") 3 else 1,
                        keyboardOptions = if (extra.type == "number") {
                            KeyboardOptions(keyboardType = KeyboardType.Number)
                        } else {
                            KeyboardOptions.Default
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 8.dp)
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = onNext,
                enabled = selected != null,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    if (isLast) "See my rules" else "Next",
                    fontSize = 17.sp,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}
