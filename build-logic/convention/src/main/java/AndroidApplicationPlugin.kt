import com.android.build.api.dsl.ApplicationExtension
import io.loperilla.convention.utils.ExtensionType
import io.loperilla.convention.utils.configCompileSdkVersion
import io.loperilla.convention.utils.configMinSdkVersion
import io.loperilla.convention.utils.configTargetSdkVersion
import io.loperilla.convention.utils.configVersionCode
import io.loperilla.convention.utils.configVersionName
import io.loperilla.convention.utils.configureBuildTypes
import io.loperilla.convention.utils.configureKotlinAndroid
import io.loperilla.convention.utils.myApplicationIdConfig
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/*****
 * Project: HomeShopping
 * From: io.loperilla.convention
 * Created By Manuel Lopera on 1/2/25 at 17:13
 * All rights reserved 2025
 */
class AndroidApplicationPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.application")
                apply("com.google.devtools.ksp")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }
            extensions.configure<ApplicationExtension> {
                compileSdk = configCompileSdkVersion
                defaultConfig {
                    applicationId = myApplicationIdConfig
                    targetSdk = configTargetSdkVersion
                    minSdk = configMinSdkVersion
                    versionCode = configVersionCode
                    versionName = configVersionName
                }

                configureKotlinAndroid(this)

                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.APPLICATION
                )
            }
        }
    }
}