package com.mukatalab.jumpy.settings

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.intellij.ui.dsl.gridLayout.VerticalAlign
import com.intellij.ui.table.JBTable
import com.mukatalab.jumpy.services.JumpyService
import javax.swing.JComponent
import javax.swing.JPanel

/**
 * This class is essentially responsible for managing the JComponent rendered in the settings dialog.
 */
class JumpyProjectSettingsConfigurable(project: Project) : Configurable {
    private val jumpyService: JumpyService = JumpyService.getInstance(project)
    private val uiState: JumpyUiState = JumpyUiState()
    private val tableModel = JumpyTableModel(uiState)
    private var tableComponent: JBTable? = null
    private var toolbarComponent: JPanel? = null

    override fun createComponent(): JComponent {
        tableComponent = JBTable(tableModel)
        toolbarComponent =
            ToolbarDecorator.createDecorator(tableComponent!!).apply {
                setAddAction {
                    uiState.addTestSrcRoots()
                    tableModel.fireTableDataChanged()
//                    tableComponent?.requestFocus()
                    tableComponent?.editCellAt(uiState.testSrcRoots.lastIndex, 0)
                }
                setRemoveAction {
                    uiState.removeTestSrcRoots(tableComponent?.selectedRow ?: return@setRemoveAction)
                    tableModel.fireTableDataChanged()
                }
            }
                .createPanel()


        return panel {
            row {
                comment(
                    "Specify all the test source roots.<br/>Path should be relative to project's content root (e.g. tests/unit).",
                ).resizableColumn().horizontalAlign(HorizontalAlign.FILL)
                button("Guess Test Sources") {
                    uiState.testSrcRoots = jumpyService.guessTestSrcRoots()
                    tableModel.fireTableDataChanged()
                }
            }
            row {
                cell(
                    toolbarComponent!!
                ).resizableColumn().horizontalAlign(HorizontalAlign.FILL).verticalAlign(VerticalAlign.FILL)

            }.resizableRow()
            row {
                comment("Note: <br />  - \"Jump to Source File\" Action ID is \"jumpy.JumpToSourceFile\"<br />  - \"Jump to...\" Action Group ID is \"jumpy.JumpToActionGroup\"<br />  - Please restart IDE after updating configuration.")
            }
        }

    }

    override fun isModified(): Boolean {
        return this.uiState.isModified(jumpyService.state)
    }

    override fun reset() {
//        In case a table row is being edited
        this.tableComponent?.cellEditor?.stopCellEditing()
        this.uiState.reset(jumpyService.state)
        this.tableModel.fireTableDataChanged()
    }

    override fun apply() {
        this.jumpyService.state = this.uiState.toServiceState()
    }

    override fun disposeUIResources() {
        toolbarComponent = null
        tableComponent = null
    }

    override fun getDisplayName(): String {
        return "JumPy"
    }

}