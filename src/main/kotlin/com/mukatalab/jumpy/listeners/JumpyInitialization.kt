package com.mukatalab.jumpy.listeners

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.mukatalab.jumpy.actions.JumpToActionGroup

class JumpyInitialization : StartupActivity, DumbAware {
    override fun runActivity(project: Project) {
        val actionManagerService: ActionManager = ActionManager.getInstance()
        val jumpToAction = actionManagerService.getAction("jumpy.JumpToActionGroup")

        if (jumpToAction is JumpToActionGroup) {
            jumpToAction.loadChildren(project)
        }
    }
}