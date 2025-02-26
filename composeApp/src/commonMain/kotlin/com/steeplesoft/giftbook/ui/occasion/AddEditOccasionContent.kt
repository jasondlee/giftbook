package com.steeplesoft.giftbook.ui.occasion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ch.benlu.composeform.fields.DateField
import ch.benlu.composeform.fields.TextField

@Composable
fun addEditOccasion(
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
                TextField(
                    label = "Occasion Name",
                    form = form,
                    fieldState = form.name,
                ).Field()
                DateField(
                    label = "Occasion Date",
                    form = form,
                    fieldState = form.eventDate
                ).Field()

                Button(onClick = {
                    component.save()
                }) {
                    Text("Save")
                }
            }
        }
    }
}
