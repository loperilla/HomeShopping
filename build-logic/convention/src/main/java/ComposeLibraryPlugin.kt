package io.loperilla.convention

import com.android.build.api.dsl.LibraryExtension
import io.loperilla.convention.utils.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

/*****
 * Project: HomeShopping
 * From: plugins
 * Created By Manuel Lopera on 1/2/25 at 13:04
 * All rights reserved 2025
 */
class ComposeLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("io.loperilla.homeshopping.library")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
        }
    }
}