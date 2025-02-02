package io.loperilla.convention.utils

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/*****
 * Project: HomeShopping
 * From: io.loperilla.convention.utils
 * Created By Manuel Lopera on 1/2/25 at 17:07
 * All rights reserved 2025
 */

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.run {
        buildFeatures {
            compose = true
            buildConfig = true
        }
        dependencies {
            val bom = libs.findLibrary("compose-bom").get()
            "implementation"(platform(bom))
            "androidTestImplementation"(platform(bom))
//            "debugImplementation"(libs.findLibrary("androidx.ui.tooling.preview").get())
        }
    }
}