package com.steeplesoft.giftbook.form

import androidx.compose.runtime.mutableStateOf
import com.steeplesoft.camper.FieldState
import com.steeplesoft.camper.Form
import com.steeplesoft.camper.Validator
import com.steeplesoft.camper.validators.NotBlankValidator
import com.steeplesoft.giftbook.model.GiftIdea

class IdeaForm(val idea : GiftIdea? = null) : Form() {
    override fun getFormFields() = listOf(title, notes, estimatedCost)

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

class IntegerValidator(errorText: String? = null) : Validator<String?>(
    validate = {
        parseNonNegativeInt(it.orEmpty()) != null
    },
    errorText = errorText ?: "Enter a non-negative whole number"
)
