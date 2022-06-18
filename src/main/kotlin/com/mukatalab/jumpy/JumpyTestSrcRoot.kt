package com.mukatalab.jumpy

import java.util.*

data class JumpyTestSrcRoot(private var _path: String = "", var name: String = "") {
    var path: String
        get() = _path
        set(value) {
            _path = JumpyPath(value).path
        }


    val jumpyPath: JumpyPath
        get() = JumpyPath(path)

    val actionId: String
        get() = "jumpy.JumpTo${pascalCaseName(this.name)}File"


    private fun pascalCaseName(text: String) = text.split(' ')
        .map { subString -> subString.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } }
        .joinToString("")

}