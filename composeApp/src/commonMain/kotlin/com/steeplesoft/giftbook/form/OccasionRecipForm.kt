package com.steeplesoft.giftbook.form

import androidx.compose.runtime.mutableStateOf
import com.steeplesoft.camper.FieldState
import com.steeplesoft.camper.Form
import com.steeplesoft.camper.Validator
import com.steeplesoft.giftbook.model.OccasionRecipient

class OccasionRecipForm(val occasionRecip: OccasionRecipient?) : Form() {

    override fun self(): Form {
        return this
    }

    override fun getFormFields() = listOf(count, cost)

    val count : FieldState<Int?> = FieldState(
        state = mutableStateOf(occasionRecip?.targetCount ?: 0),
        validators = mutableListOf(GreaterThanZeroValidator())
    )

    val cost : FieldState<Int?> = FieldState(
        state = mutableStateOf(occasionRecip?.targetCost ?: 0),
        validators = mutableListOf(GreaterThanZeroValidator())
    )
}

class GreaterThanZeroValidator(errorText: String = "This field must be greater than zero") : Validator<Int?>(
    validate = {
        it != null && it > 0
    },
    errorText = errorText
)

