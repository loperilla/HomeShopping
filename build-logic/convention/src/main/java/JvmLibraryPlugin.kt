import io.loperilla.convention.utils.configureKotlinJvm
import io.loperilla.convention.utils.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/*****
 * Project: HomeShopping
 * From: io.loperilla.convention
 * Created By Manuel Lopera on 1/2/25 at 17:25
 * All rights reserved 2025
 */
class JvmLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("org.jetbrains.kotlin.jvm")
            configureKotlinJvm()

            dependencies {
                "implementation"(target.libs.findLibrary("koin-core").get())
                "testImplementation"(target.libs.findBundle("unit-test").get())
            }
        }
    }
}