package com.steeplesoft.giftbook.form

import androidx.compose.runtime.mutableStateOf
import com.steeplesoft.camper.FieldState
import com.steeplesoft.camper.Form
import com.steeplesoft.camper.validators.NotBlankValidator
import com.steeplesoft.giftbook.model.Recipient

class RecipientForm(val recipient: Recipient?) : Form() {
    override fun self(): Form {
        return this
    }
    override fun getFormFields() = listOf(name)

    val name : FieldState<String?> = FieldState(
        state = mutableStateOf(recipient?.name),
        validators = mutableListOf(NotBlankValidator())
    )
}
