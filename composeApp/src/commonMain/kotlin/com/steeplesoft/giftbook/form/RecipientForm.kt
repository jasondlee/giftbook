package com.steeplesoft.giftbook.form

import androidx.compose.runtime.mutableStateOf
import com.steeplesoft.giftbook.model.Recipient
import com.steeplesoft.kmpform.FieldState
import com.steeplesoft.kmpform.Form
import com.steeplesoft.kmpform.validators.NotEmptyValidator

class RecipientForm(val recipient: Recipient?) : Form() {
    override fun self(): Form {
        return this
    }
    override fun getFormFields() = listOf(name)

    val name : FieldState<String?> = FieldState(
        state = mutableStateOf(recipient?.name),
        validators = mutableListOf(NotEmptyValidator())
    )
}
