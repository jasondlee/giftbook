package com.steeplesoft.giftbook.ui.idea

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import com.steeplesoft.giftbook.database.model.GiftIdea
import com.steeplesoft.kmpform.FieldState
import com.steeplesoft.kmpform.Form
import com.steeplesoft.kmpform.Validator
import com.steeplesoft.kmpform.validators.NotEmptyValidator

class IdeaForm(val idea : GiftIdea? = null) : Form() {
    override fun self(): Form {
        return this
    }

    override fun getFormFields() = listOf(title)

    val title : FieldState<String?> = FieldState(
        state = mutableStateOf(idea?.title),
        validators = mutableListOf(NotEmptyValidator()))
    val notes : FieldState<String?> = FieldState(
        state = mutableStateOf(idea?.notes))
    val estimatedCost : FieldState<String?> = FieldState(
        state = mutableStateOf(idea?.estimatedCost?.toString() ?: ""),
        validators = mutableListOf(IntegerValidator())
    )
}

class IntegerValidator<T>(errorText: String? = null) : Validator<T>(
    validate = {
        it != null && it is Int
    },
    errorText = errorText ?: "This field should not be empty"
)
