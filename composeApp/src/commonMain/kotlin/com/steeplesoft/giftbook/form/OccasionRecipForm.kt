package com.steeplesoft.giftbook.form

import androidx.compose.runtime.mutableStateOf
import com.steeplesoft.giftbook.model.OccasionRecipient
import com.steeplesoft.kmpform.FieldState
import com.steeplesoft.kmpform.Form

class OccasionRecipForm(val occasionRecip: OccasionRecipient?) : Form() {

    override fun self(): Form {
        return this
    }

    override fun getFormFields() = listOf(count, cost)

    val count : FieldState<Int?> = FieldState(state = mutableStateOf(occasionRecip?.targetCount))

    val cost : FieldState<Int?> = FieldState(state = mutableStateOf(occasionRecip?.targetCost))
}
