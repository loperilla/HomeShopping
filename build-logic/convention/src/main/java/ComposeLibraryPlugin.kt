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
                apply("homeshopping.android.library")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
        }
    }
}