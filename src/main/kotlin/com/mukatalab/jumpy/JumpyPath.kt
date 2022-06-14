package com.mukatalab.jumpy


data class JumpyPath(val locations: List<String> = listOf()) {
    constructor(path: String) : this(parsePath(path))

    /**
     * Subtract to produce a relative path sequence.
     *
     */
    operator fun minus(operand: JumpyPath): JumpyPath {
        if (!hasParent(operand)) {
            throw Exception("prefix must match operand")
        }

        return JumpyPath(this.locations.subList(operand.locations.size, this.locations.size))
    }

    fun hasParent(operand: JumpyPath): Boolean {
        return operand.locations == this.locations.subList(0, operand.locations.size)
    }

    operator fun plus(operand: JumpyPath): JumpyPath {
        return JumpyPath(this.locations + operand.locations)
    }


    companion object {
        /**
         * Parse path to something that is like a python resource reference. Note that it isn't quite a resource
         * reference since the prefixes aren't necessarily part of the python package.
         */
        fun parsePath(path: String): List<String> {
            return path.split("/").filter { it.isNotBlank() }.toMutableList().apply {
                val fileName = last()
                if (fileName == "__init__.py") {
                    removeLast()
                } else {
                    set(lastIndex, fileName.replace(Regex("""^test_"""), "").replace(".py", ""))
                }
            }.toList()
        }
    }
}
