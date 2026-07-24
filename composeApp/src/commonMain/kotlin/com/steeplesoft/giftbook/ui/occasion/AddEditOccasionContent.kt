package com.steeplesoft.giftbook.ui.occasion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.steeplesoft.camper.fields.ComboBoxField
import com.steeplesoft.giftbook.theme.Spacing
import com.steeplesoft.camper.fields.DateField
import com.steeplesoft.camper.fields.TextField
import com.steeplesoft.giftbook.ui.general.SaveCancelButtons
import com.steeplesoft.giftbook.ui.general.StandardHeader

@Composable
fun AddEditOccasion(
    component: AddEditOccasionComponent,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val form = component.form
        LazyColumn(
            modifier = modifier.padding(Spacing.screenPadding),
            verticalArrangement = Arrangement.spacedBy(Spacing.fieldSpacing)
        ) {
            item {
                StandardHeader(text = component.occasion?.let { "Edit Occasion" } ?: "Add New Occasion")
                TextField(
                    label = "Occasion Name",
                    form = form,
                    fieldState = form.name,
                ).Field()

                ComboBoxField(
                    modifier = Modifier.padding(bottom = Spacing.internalPadding),
                    label = "Event Type",
                    form = component.form,
                    fieldState = component.form.eventType
                ).Field()

                DateField(
                    label = "Occasion Date",
                    form = form,
                    fieldState = form.eventDate
                ).Field()

                SaveCancelButtons(
                    onSave = { component.save() },
                    onCancel = { component.cancel() }
                )
            }
        }
    }
}
