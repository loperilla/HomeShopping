import com.android.build.api.dsl.LibraryExtension
import io.loperilla.convention.utils.ExtensionType
import io.loperilla.convention.utils.configureBuildTypes
import io.loperilla.convention.utils.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/*****
 * Project: HomeShopping
 * From: io.loperilla.convention
 * Created By Manuel Lopera on 1/2/25 at 17:21
 * All rights reserved 2025
 */
class LibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("com.google.devtools.ksp")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)

                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.LIBRARY
                )

                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
            }

            dependencies {

            }
        }
    }
}