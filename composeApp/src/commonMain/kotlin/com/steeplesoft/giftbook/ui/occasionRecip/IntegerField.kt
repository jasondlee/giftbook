package com.steeplesoft.giftbook.ui.occasionRecip

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.steeplesoft.kmpform.Field
import com.steeplesoft.kmpform.FieldState
import com.steeplesoft.kmpform.Form
import com.steeplesoft.kmpform.components.TextFieldComponent

class IntegerField(
    label: String,
    form: Form,
    modifier: Modifier? = Modifier,
    fieldState: FieldState<Int?>,
    isEnabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    changed: ((v: Int?) -> Unit)? = null
) : Field<Int?>(
    label = label,
    form = form,
    fieldState = fieldState,
    isEnabled = isEnabled,
    modifier = modifier,
    imeAction = imeAction,
    keyboardType = KeyboardType.Number,
    visualTransformation = visualTransformation,
    changed = changed
) {

    fun onChange(v: String) {
        try {
            if (v.isNotEmpty()) {
                this.value.value = v.toInt()
//        this.updateFormValue()
                fieldState.state.value = this.value.value
                fieldState.hasChanges.value = true

                form.validate()
                changed?.invoke(this.value.value)
            } else {
                fieldState.state.value = null
                fieldState.hasChanges.value = true
            }
        } catch (nfe: NumberFormatException) {
//            fieldState.isValid.value = false
//            fieldState.errorText += "Invalid number entered"
        }
    }

    /**
     * Returns a composable representing the DateField / Picker for this field
     */
    @Composable
    override fun Field() {
        this.updateComposableValue()
        if (!fieldState.isVisible()) {
            return
        }

        TextFieldComponent(
            modifier = modifier ?: Modifier,
            imeAction = imeAction ?: ImeAction.Next,
            isEnabled = isEnabled,
            keyBoardActions = KeyboardActions.Default,
            keyboardType = keyboardType,
            onChange = {
                this.onChange(it)
            },
            label = label,
            text = (value.value?.toString() ?: ""),
            hasError = fieldState.hasError(),
            errorText = fieldState.errorText,
            visualTransformation = visualTransformation
        )
    }
}
