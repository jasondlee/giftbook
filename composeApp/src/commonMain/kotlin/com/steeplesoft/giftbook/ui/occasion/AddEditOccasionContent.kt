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
import androidx.compose.ui.unit.sp
import com.steeplesoft.kmpform.fields.ComboBoxField
import com.steeplesoft.kmpform.fields.DateField
import com.steeplesoft.kmpform.fields.TextField

@Composable
fun AddEditOccasion(
    component: AddEditOccasionComponent,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val form = component.form
        LazyColumn(
            modifier = modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(text = component.occasion?.let { "Edit Occasion" } ?: "Add New Occasion", fontSize = 30.sp)
                TextField(
                    label = "Occasion Name",
                    form = form,
                    fieldState = form.name,
                ).Field()

                ComboBoxField(
                    modifier = Modifier.padding(bottom = 8.dp),
                    label = "Event Type",
                    form = component.form,
                    fieldState = component.form.eventType
                ).Field()

                DateField(
                    label = "Occasion Date",
                    form = form,
                    fieldState = form.eventDate
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
}
