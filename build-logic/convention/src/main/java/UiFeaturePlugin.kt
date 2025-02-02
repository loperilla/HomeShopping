import io.loperilla.convention.utils.addUiLayerDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/*****
 * Project: HomeShopping
 * From: io.loperilla.convention
 * Created By Manuel Lopera on 1/2/25 at 17:18
 * All rights reserved 2025
 */
class UiFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("homeshopping.library.compose")
            }

            dependencies {
                addUiLayerDependencies(target)
            }
        }
    }
}