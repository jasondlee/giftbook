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
import androidx.compose.ui.unit.sp
import com.steeplesoft.kmpform.fields.TextField

@Composable
fun AddEditIdea(
    component: AddEditIdeaComponent,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val form = component.form
        Text(text = "Gift Idea for ${component.recipient.name}",
            fontWeight = FontWeight.Companion.Bold, fontSize = 30.sp)
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
