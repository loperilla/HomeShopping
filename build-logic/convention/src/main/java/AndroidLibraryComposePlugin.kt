import com.android.build.api.dsl.LibraryExtension
import io.loperilla.convention.utils.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

/*****
 * Project: HomeShopping
 * From: io.loperilla.convention
 * Created By Manuel Lopera on 1/2/25 at 17:20
 * All rights reserved 2025
 */
class AndroidLibraryComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("homeshopping.android.library")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
        }
    }
}