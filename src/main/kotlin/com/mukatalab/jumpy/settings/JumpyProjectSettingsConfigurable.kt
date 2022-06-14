package com.mukatalab.jumpy.settings

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import javax.swing.JComponent

/**
 * This class is essentially responsible for managing the JComponent rendered in the settings dialog.
 */
class JumpyProjectSettingsConfigurable(private val project: Project) : Configurable {
    override fun createComponent(): JComponent? {
        TODO("Not yet implemented")
    }

    override fun isModified(): Boolean {
        TODO("Not yet implemented")
    }

    override fun apply() {
        TODO("Not yet implemented")
    }

    override fun getDisplayName(): String {
        return "JumPy"
    }

}