package com.mukatalab.jumpy.actions

import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.project.Project
import com.mukatalab.jumpy.services.JumpyService

class JumpToActionGroup(var children: Array<AnAction> = arrayOf()) : ActionGroup() {
    override fun getChildren(e: AnActionEvent?): Array<AnAction> {
        return children
    }

    fun loadChildren(project: Project) {
        val pluginId: PluginId = PluginId.findId("org.mukatalab.jumpy") ?: return
        val actionManagerService: ActionManager = ActionManager.getInstance()

        val jumpToSrcAction = JumpToAction(null, "Jump to Source File")
        actionManagerService.registerAction("jumpy.JumpToSourceFile", jumpToSrcAction, pluginId)

        children =
            arrayOf<AnAction>(jumpToSrcAction) + JumpyService.getInstance(project).state.testSrcRoots.map { testSrcRoot ->
                JumpToAction(testSrcRoot, "Jump to ${testSrcRoot.name} File").also { testSrcAction ->
                    actionManagerService.registerAction(testSrcRoot.actionId, testSrcAction, pluginId)
                }
            }.toTypedArray()
    }
}
