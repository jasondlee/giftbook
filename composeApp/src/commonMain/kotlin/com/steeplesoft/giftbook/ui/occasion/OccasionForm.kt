package com.steeplesoft.giftbook.ui.occasion

import androidx.compose.runtime.mutableStateOf
import com.steeplesoft.giftbook.database.model.Occasion
import com.steeplesoft.giftbook.now
import com.steeplesoft.kmpform.FieldState
import com.steeplesoft.kmpform.Form
import com.steeplesoft.kmpform.validators.NotEmptyValidator
import kotlinx.datetime.LocalDate

class OccasionForm(val occasion: Occasion?) : Form() {

    override fun self(): Form {
        return this
    }

    override fun getFormFields() = listOf(name, eventDate)

    val name: FieldState<String?> = FieldState(
        state = mutableStateOf(occasion?.name),
        validators = mutableListOf(NotEmptyValidator()),
    )
    val eventDate: FieldState<LocalDate?> = FieldState(
        state = mutableStateOf(occasion?.eventDate ?: LocalDate.now())
    )
}
