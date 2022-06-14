package com.mukatalab.jumpy.settings

import javax.swing.table.AbstractTableModel

class JumpyTableModel : AbstractTableModel() {
    private val _columnNames = arrayOf("Name", "Relative Path")
    private val _columnClasses = arrayOf(String::class.java, String::class.java)

    override fun getRowCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getColumnCount(): Int {
        return _columnClasses.size
    }

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        TODO("Not yet implemented")
    }

}