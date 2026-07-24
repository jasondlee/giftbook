package com.steeplesoft.giftbook.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnResume
import com.steeplesoft.camper.components.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

/**
 * Common extension functions for Decompose components to reduce boilerplate.
 */

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
    doOnResume {
        CoroutineScope(Dispatchers.IO).launch {
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

/**
 * Execute a block on the Main dispatcher (for navigation and UI updates).
 * 
 * Usage:
 * ```
 * fun save() {
 *     onMain {
 *         dao.save(entity)
 *         nav.pop()
 *     }
 * }
 * ```
 */
fun onMain(block: suspend () -> Unit) {
    CoroutineScope(Dispatchers.Main).launch {
        block()
    }
}

/**
 * Execute a block on the IO dispatcher (for database operations).
 * 
 * Usage:
 * ```
 * fun loadData() {
 *     onIO {
 *         val data = dao.getData()
 *         items.update { data }
 *     }
 * }
 * ```
 */
fun onIO(block: suspend () -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        block()
    }
}
