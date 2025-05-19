package com.steeplesoft.giftbook.ui.occasion

import androidx.compose.runtime.mutableStateOf
import com.steeplesoft.giftbook.database.model.EventType
import com.steeplesoft.giftbook.database.model.Occasion
import com.steeplesoft.giftbook.now
import com.steeplesoft.kmpform.FieldState
import com.steeplesoft.kmpform.Form
import com.steeplesoft.kmpform.fields.PickerValue
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
    val eventType = FieldState(
        state = mutableStateOf(EventTypeOption.of(occasion?.eventType)),
        options = mutableListOf(
            EventTypeOption.BIRTHDAY,
            EventTypeOption.CHRISTMAS,
            EventTypeOption.ANNIVERSARY,
            EventTypeOption.GRADUATION,
            EventTypeOption.VALENTINES,
            EventTypeOption.OTHER
        ),
        optionItemFormatter = { it?.eventType?.label ?: "Unknown" },
        validators = mutableListOf(
            NotEmptyValidator()
        )
    )
}

data class EventTypeOption(val eventType: EventType) : PickerValue() {
    override fun searchFilter(query: String): Boolean {
        return this.eventType.label.startsWith(query)
    }


    companion object {
        val BIRTHDAY = EventTypeOption(EventType.BIRTHDAY)
        val CHRISTMAS = EventTypeOption(EventType.CHRISTMAS)
        val ANNIVERSARY = EventTypeOption(EventType.ANNIVERSARY)
        val GRADUATION = EventTypeOption(EventType.GRADUATION)
        val VALENTINES = EventTypeOption(EventType.VALENTINES)
        val OTHER = EventTypeOption(EventType.OTHER)

        fun of(type: EventType?): EventTypeOption? {
            return when (type) {
                EventType.BIRTHDAY -> BIRTHDAY
                EventType.CHRISTMAS -> CHRISTMAS
                EventType.ANNIVERSARY -> ANNIVERSARY
                EventType.GRADUATION -> GRADUATION
                EventType.VALENTINES -> VALENTINES
                EventType.OTHER -> OTHER
                else -> null
            }
        }
    }
}
