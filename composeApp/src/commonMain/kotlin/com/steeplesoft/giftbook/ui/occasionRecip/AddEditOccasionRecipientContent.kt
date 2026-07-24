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
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.steeplesoft.giftbook.theme.Spacing
import com.steeplesoft.giftbook.theme.Typography
import com.steeplesoft.camper.components.AsyncLoad
import com.steeplesoft.camper.components.ComboBox
import com.steeplesoft.camper.fields.IntegerField
import com.steeplesoft.giftbook.model.Recipient
import com.steeplesoft.giftbook.ui.general.SaveCancelButtons
import com.steeplesoft.giftbook.ui.general.StandardHeader

@Composable
fun AddEditOccasionRecipient(
    component: AddEditOccasionRecipientComponent,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.fieldSpacing)
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
                fontSize = Typography.primaryTextSize,
            )

            if (component.recipient != null) {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Recipient: ")
                        }
                        append(component.recipient!!.name)
                    },
                    fontSize = Typography.primaryTextSize,
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

            SaveCancelButtons(
                onSave = { component.save() },
                onCancel = { component.cancel() }
            )
        }
    }
}
