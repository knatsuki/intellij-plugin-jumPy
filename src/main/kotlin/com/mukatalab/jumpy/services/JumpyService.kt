package com.mukatalab.jumpy.services

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.vfs.VirtualFile
import com.mukatalab.jumpy.JumpyPath
import com.mukatalab.jumpy.JumpyTestSrcRoot

@State(
    name = "com.mukatalab.jumpy.services.JumpyService",
    storages = [Storage("JumpyProjectConfiguration.xml")]
)
class JumpyService(val project: Project) : PersistentStateComponent<JumpyServiceState> {
    private var _state = JumpyServiceState()

    override fun getState(): JumpyServiceState {
        return _state
    }

    fun setState(state: JumpyServiceState) {
        _state = state
    }

    override fun loadState(state: JumpyServiceState) {
        _state = state
    }


    /**
     * @param pyPath: Content-root relative path sequence
     */
    fun buildSearchablePyPath(pyPath: JumpyPath): JumpyPath {
        for (testRoot in _state.testSrcRoots) {
            if (pyPath.hasParent(testRoot.jumpyPath)) {
                return pyPath - testRoot.jumpyPath
            }
        }

        return if (pyPath.locations.first() == "src") pyPath - JumpyPath("src") else pyPath
    }

    /**
     * Traverse down the filesystem tree, starting from content root, to find the closest match along the search path.
     */
    fun findBestFileMatch(
        contentRoot: VirtualFile,
        jumpyPath: JumpyPath,
        testSrcRoot: JumpyTestSrcRoot? = null
    ): VirtualFile {
        val locationsLeft = jumpyPath.locations.asReversed().toMutableList()

        var currentNode: VirtualFile = contentRoot
        var nextNode: VirtualFile?

//        For source jump, we need to account for possibility of packages being at contentRootDir or at contentRootDir/src
        if (testSrcRoot == null) {
            nextNode = currentNode.findChild("src")
            if (nextNode?.findChild(locationsLeft[0]) != null) {
                currentNode = nextNode
            }
        } else {
            locationsLeft.addAll(testSrcRoot.jumpyPath.locations.asReversed())
        }

//        Traverse down

        while (locationsLeft.size > 0) {
            val name = locationsLeft.removeLast()

            nextNode = currentNode.findChild(name)

            if (nextNode != null) {
                currentNode = nextNode
                continue
            }

//            If directory not found, see if a file of the name can be found
            val fileName = if (testSrcRoot != null) "test_${name}.py" else "${name}.py"

            nextNode = currentNode.findChild(fileName)

            if (nextNode != null) {
                return nextNode
            }

            return currentNode
        }
        return currentNode
    }

    /**
     * Try to guess the test src roots
     */
    fun guessTestSrcRoots(): MutableList<JumpyTestSrcRoot> {
        val testSrcRoots = mutableListOf<JumpyTestSrcRoot>()
        val projectRoot = project.guessProjectDir() ?: return testSrcRoots

        val testDirChild = projectRoot.findChild("tests") ?: projectRoot.findChild("test") ?: return testSrcRoots
        testDirChild.children.forEach {
            when (it.name) {
                "unit", "functional", "integration" -> testSrcRoots.add(
                    JumpyTestSrcRoot(
                        "${it.parent.name}/${it.name}",
                        "${it.name.replaceFirstChar { char -> char.uppercase() }} Test"
                    )
                )
            }
        }

        if (testSrcRoots.isEmpty()) {
            testSrcRoots.add(
                JumpyTestSrcRoot(
                    testDirChild.name,
                    testDirChild.name.replaceFirstChar { char -> char.uppercase() })
            )
        }

        return testSrcRoots
    }

    companion object {
        fun getInstance(project: Project): JumpyService {
            return project.service<JumpyService>()
        }
    }

}