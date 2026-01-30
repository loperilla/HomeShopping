import com.android.build.api.dsl.LibraryExtension
import io.loperilla.convention.utils.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
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
                apply("homeshopping.android.library")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            val extension = extensions.getByType<LibraryExtension>()
            configureComposeLibraryExtension(extension)
        }
    }
}

internal fun Project.configureComposeLibraryExtension(libraryExtension: LibraryExtension) {
    libraryExtension.run {
        defaultConfig {
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
        buildFeatures {
            compose = true
            buildConfig = true
        }
        packaging {
            resources {
                pickFirsts.add("META-INF/LICENSE.md")
                pickFirsts.add("META-INF/LICENSE-notice.md")
                pickFirsts.add("META-INF/AL2.0")
                pickFirsts.add("META-INF/LGPL2.1")
            }
        }
        dependencies {
            val bom = libs.findLibrary("compose-bom").get()
            "implementation"(platform(bom))
            "implementation"(libs.findBundle("compose").get())
            "implementation"(libs.findLibrary("androidx.junit.ktx").get())
            "androidTestImplementation"(platform(bom))

//            "debugImplementation"(libs.findLibrary("androidx.ui.tooling.preview").get())
        }
    }
}