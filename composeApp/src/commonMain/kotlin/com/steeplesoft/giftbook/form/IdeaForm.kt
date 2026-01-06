package com.steeplesoft.giftbook.form

import androidx.compose.runtime.mutableStateOf
import com.steeplesoft.camper.FieldState
import com.steeplesoft.camper.Form
import com.steeplesoft.camper.Validator
import com.steeplesoft.camper.validators.NotBlankValidator
import com.steeplesoft.giftbook.model.GiftIdea

class IdeaForm(val idea : GiftIdea? = null) : Form() {
    override fun self(): Form {
        return this
    }

    override fun getFormFields() = listOf(title)

    val title : FieldState<String?> = FieldState(
        state = mutableStateOf(idea?.title),
        validators = mutableListOf(NotBlankValidator()))
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
