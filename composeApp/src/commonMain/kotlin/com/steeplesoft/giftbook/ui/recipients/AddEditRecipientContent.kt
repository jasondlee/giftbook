package com.steeplesoft.giftbook.ui.recipients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.steeplesoft.camper.fields.TextField
import com.steeplesoft.giftbook.theme.Spacing
import com.steeplesoft.giftbook.ui.general.SaveCancelButtons
import com.steeplesoft.giftbook.ui.general.StandardHeader

@Composable
fun AddEditRecipientContent(
    component: AddEditRecipientComponent,
    modifier: Modifier = Modifier
) {
    val form = component.form

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.fieldSpacing)
    ) {
        StandardHeader(text = component.recipient?.let { "Edit Recipient" } ?: "Add New Recipient")
        
        TextField(
            label = "Recipient Name",
            form = form,
            fieldState = form.name,
        ).Field()

        SaveCancelButtons(
            onSave = { component.save() },
            onCancel = { component.cancel() }
        )
    }
}
