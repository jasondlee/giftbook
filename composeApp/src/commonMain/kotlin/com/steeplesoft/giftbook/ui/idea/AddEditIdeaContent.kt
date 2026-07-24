package com.steeplesoft.giftbook.ui.idea

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.steeplesoft.camper.fields.TextField
import com.steeplesoft.giftbook.theme.Spacing
import com.steeplesoft.giftbook.ui.general.SaveCancelButtons
import com.steeplesoft.giftbook.ui.general.StandardHeader

@Composable
fun AddEditIdea(
    component: AddEditIdeaComponent,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(Spacing.screenPadding),
        verticalArrangement = Arrangement.spacedBy(Spacing.fieldSpacing)
    ) {
        val form = component.form
        StandardHeader(text = "Gift Idea for ${component.recipient.name}")
        TextField(
            label = "Gift Idea",
            form = form,
            fieldState = form.title,
        ).Field()
        TextField(
            label = "Notes",
            form = form,
            fieldState = form.notes,
        ).Field()
        TextField(
            label = "Estimated Cost",
            form = form,
            fieldState = form.estimatedCost,
        ).Field()

        SaveCancelButtons(
            onSave = { component.save() },
            onCancel = { component.cancel() }
        )
    }
}
