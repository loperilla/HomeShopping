package io.loperilla.convention.utils

import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

/*****
 * Project: HomeShopping
 * From: plugins.utils
 * Created By Manuel Lopera on 1/2/25 at 12:53
 * All rights reserved 2025
 */

fun DependencyHandlerScope.addUiLayerDependencies(project: Project) {
    "implementation"(project(":core:presentation:ui"))
    "implementation"(project(":core:presentation:designsystem"))

    "implementation"(project.libs.findBundle("koin.compose").get())
    "implementation"(project.libs.findBundle("compose").get())
//    "debugImplementation"(project.libs.findBundle("compose.debug").get())
}