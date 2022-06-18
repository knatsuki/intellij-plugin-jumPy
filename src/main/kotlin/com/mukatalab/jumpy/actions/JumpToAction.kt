package com.mukatalab.jumpy.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectFileIndex
import com.intellij.openapi.util.NlsActions
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.mukatalab.jumpy.JumpyPath
import com.mukatalab.jumpy.JumpyTestSrcRoot
import com.mukatalab.jumpy.services.JumpyService
import javax.swing.Icon

class JumpToAction(
    private val testSrcRoot: JumpyTestSrcRoot?,
    text: @NlsActions.ActionText String? = null,
    description: @NlsActions.ActionDescription String? = null,
    icon: Icon? = null
) : AnAction(text, description, icon) {
    override fun actionPerformed(e: AnActionEvent) {
        val project: Project = e.project ?: return
        val psiFile: PsiFile = e.getData(CommonDataKeys.PSI_FILE) ?: return


        val file: VirtualFile = psiFile.virtualFile
        val fileJumpyPath = JumpyPath(file.path)
        val contentRoot: VirtualFile =
            ProjectFileIndex.getInstance(project).getContentRootForFile(psiFile.virtualFile) ?: return
        val contentRootJumpyPath = JumpyPath(contentRoot.path)

        val jumpyService = JumpyService.getInstance(project)

//        This shouldn't happen since contentRoot is extracted from file
        if (!fileJumpyPath.hasParent(contentRootJumpyPath)) return
        val relativeJumpyPath = JumpyPath(file.path) - JumpyPath(contentRoot.path)

        val searchablePyPath = jumpyService.buildSearchablePyPath(relativeJumpyPath)
        val bestMatchedFile = jumpyService.findBestFileMatch(contentRoot, searchablePyPath, testSrcRoot)

        navigateToFile(project, bestMatchedFile)
    }

    private fun navigateToFile(project: Project, file: VirtualFile) {
        OpenFileDescriptor(
            project, file
        ).navigate(true)
    }
}


