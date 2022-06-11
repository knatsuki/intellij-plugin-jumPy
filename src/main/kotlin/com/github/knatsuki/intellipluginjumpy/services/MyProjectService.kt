package com.github.knatsuki.intellipluginjumpy.services

import com.intellij.openapi.project.Project
import com.github.knatsuki.intellipluginjumpy.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
