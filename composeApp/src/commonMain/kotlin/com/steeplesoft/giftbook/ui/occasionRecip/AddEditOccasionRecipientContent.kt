package com.steeplesoft.giftbook.ui.occasionRecip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.steeplesoft.camper.components.AsyncLoad
import com.steeplesoft.camper.components.ComboBox
import com.steeplesoft.camper.fields.IntegerField
import com.steeplesoft.giftbook.model.Recipient

@Composable
fun AddEditOccasionRecipient(
    component: AddEditOccasionRecipientComponent,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val status by component.requestStatus.subscribeAsState()

        AsyncLoad(status) {
            val form = component.form

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Occasion: ")
                    }
                    append(component.occasion.name)
                },
                fontSize = 20.sp,
            )

            if (component.recipient != null) {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Recipient: ")
                        }
                        append(component.recipient!!.name)
                    },
                    fontSize = 20.sp,
                )
            } else {
                val recipients by component.recipients.subscribeAsState()

                val current: Recipient? by remember { mutableStateOf(component.recipient) }

                ComboBox(label = "Recipient",
                    selected = current,
                    onChange = { newValue ->
                        component.recipient = newValue
                    },
                    items = recipients,
                    itemLabel = { recip -> recip?.name ?: "--" }
                )
            }

            IntegerField(
                label = "Target Count",
                form = form,
                fieldState = form.count,
            ).Field()

            IntegerField(
                label = "Target Cost",
                form = form,
                fieldState = form.cost,
            ).Field()

            Row(modifier = Modifier.padding(top = 5.dp).fillMaxWidth()) {
                Button(
                    onClick = { component.save() },
                    modifier = Modifier.padding(end = 3.dp)
                        .fillMaxWidth(0.5f)
                ) {
                    Text("Save")
                }

                Button(
                    onClick = { component.cancel() },
                    modifier = Modifier.padding(start = 3.dp)
                        .fillMaxWidth()
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}
