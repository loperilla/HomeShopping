package io.loperilla.convention.utils

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.getByType


/*****
 * Project: HomeShopping
 * From: plugins.utils
 * Created By Manuel Lopera on 1/2/25 at 12:38
 * All rights reserved 2025
 */

val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun DependencyHandlerScope.tests(target: Project) {
    "testImplementation"(target.libs.findLibrary("junit").get())
    "androidTestImplementation"(target.libs.findLibrary("androidx.compose.ui.test.junit4").get())
    "androidTestImplementation"(target.libs.findLibrary("test.ext.junit").get())
    "androidTestImplementation"(target.libs.findLibrary("test.espresso").get())
    "androidTestImplementation"(target.libs.findLibrary("compose.ui.test").get())
    "debugImplementation"(target.libs.findLibrary("compose.ui.tooling").get())
    "debugImplementation"(target.libs.findLibrary("compose.ui.test.manifest").get())
//    androidTestImplementation(platform(libs.compose.bom))
}
