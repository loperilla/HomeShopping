import io.loperilla.convention.utils.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project

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
        }
    }
}