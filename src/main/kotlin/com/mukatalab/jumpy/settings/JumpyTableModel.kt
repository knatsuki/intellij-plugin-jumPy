package com.mukatalab.jumpy.settings

import javax.swing.table.AbstractTableModel

class JumpyTableModel(val uiState: JumpyUiState) : AbstractTableModel() {
    private val _columnNames = arrayOf("Name", "Relative Path", "Action ID")
    private val _columnClasses = arrayOf(String::class.java, String::class.java, String::class.java)

    override fun getRowCount(): Int {
        return uiState.testSrcRoots.size
    }

    override fun getColumnCount(): Int {
        return _columnClasses.size
    }

    override fun getColumnName(column: Int): String {
        return _columnNames[column]
    }

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        val testSrcRoot = uiState.testSrcRoots[rowIndex]

        return when (columnIndex) {
            0 -> testSrcRoot.name
            1 -> testSrcRoot.path
            2 -> testSrcRoot.actionId
            else -> throw IndexOutOfBoundsException()
        }
    }

    override fun isCellEditable(rowIndex: Int, columnIndex: Int): Boolean {
        return columnIndex != 2
    }

    override fun setValueAt(aValue: Any?, rowIndex: Int, columnIndex: Int) {
        when (columnIndex) {
            0 -> uiState.setTestSrcRootName(rowIndex, aValue as String)
            1 -> uiState.setTestSrcRootPath(rowIndex, aValue as String)
            else -> throw IndexOutOfBoundsException()
        }
    }
}


