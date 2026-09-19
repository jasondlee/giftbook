package com.steeplesoft.giftbook.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnResume
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlin.coroutines.CoroutineContext
import com.steeplesoft.camper.components.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * Common extension functions for Decompose components to reduce boilerplate.
 */

fun ComponentContext.componentScope(context: CoroutineContext = Dispatchers.Main): CoroutineScope {
    val scope = CoroutineScope(SupervisorJob() + context)
    lifecycle.doOnDestroy(scope::cancel)
    return scope
}

/**
 * Standard pattern for loading data on component resume.
 * Automatically sets status to LOADING, executes the load block, and sets status to SUCCESS.
 * 
 * Usage:
 * ```
 * init {
 *     loadOnResume(requestStatus) {
 *         val data = dao.getData()
 *         items.update { data }
 *     }
 * }
 * ```
 */
fun ComponentContext.loadOnResume(
    statusValue: MutableValue<Status>,
    loadBlock: suspend () -> Unit
) {
    val scope = componentScope()
    doOnResume {
        scope.launch(Dispatchers.IO) {
            statusValue.update { Status.LOADING }
            try {
                loadBlock()
                statusValue.update { Status.SUCCESS }
            } catch (e: Exception) {
                statusValue.update { Status.ERROR }
                // Log error appropriately
            }
        }
    }
}
