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
import androidx.compose.ui.unit.dp
import com.steeplesoft.kmpform.fields.TextField

@Composable
fun AddEditRecipientContent(
    component: AddEditRecipientComponent,
    modifier: Modifier = Modifier
) {
    val form = component.form

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            label = "Recipient Name",
            form = form,
            fieldState = form.name,
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
