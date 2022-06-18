package com.mukatalab.jumpy.settings

import com.mukatalab.jumpy.JumpyTestSrcRoot
import com.mukatalab.jumpy.services.JumpyServiceState

/**
 * My assumption is that this data will only be updated via UI thread, so
 * no need to implement any thread-safety mechanism.
 */
data class JumpyUiState(var testSrcRoots: MutableList<JumpyTestSrcRoot> = mutableListOf()) {
    fun setTestSrcRootName(index: Int, name: String) {
        this.testSrcRoots[index].name = name
    }

    fun setTestSrcRootPath(index: Int, path: String) {
        this.testSrcRoots[index].path = path
    }

    fun addTestSrcRoots() {
        this.testSrcRoots.add(JumpyTestSrcRoot())
    }

    fun removeTestSrcRoots(index: Int) {
        this.testSrcRoots.removeAt(index)
    }

    fun isModified(serviceState: JumpyServiceState): Boolean {
        return this.testSrcRoots.toList() != serviceState.testSrcRoots
    }

    /**
     * Reset the ui state from the service state
     */
    fun reset(serviceState: JumpyServiceState) {
        this.testSrcRoots = serviceState.testSrcRoots.toMutableList()
    }

    /**
     * Do a (minimal validation/cleanup for test src root's path
     */


    fun toServiceState(): JumpyServiceState {
        return JumpyServiceState(this.testSrcRoots)
    }
}
