import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import io.loperilla.convention.utils.configCompileSdkVersion
import io.loperilla.convention.utils.configMinSdkVersion
import io.loperilla.convention.utils.configureKotlin
import io.loperilla.convention.utils.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class ComposeApplicationPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("homeshopping.android.application")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidApplicationExtensionCompose(extension)
        }
    }
}

internal fun Project.configureAndroidApplicationExtensionCompose(
    applicationExtension: ApplicationExtension
) {
    applicationExtension.run {
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

internal fun Project.configureApplicationBuildType(
    applicationExtension: ApplicationExtension
) {
    applicationExtension.run {
        buildFeatures {
            buildConfig = true
        }

        buildTypes {
            debug {
                applicationIdSuffix = ".dev"
            }
            release {
                configureApplicationReleaseBuildType(applicationExtension)
            }
        }
    }
}

internal fun BuildType.configureApplicationReleaseBuildType(
    applicationExtension: ApplicationExtension
){
    isMinifyEnabled = true
    proguardFiles(
        applicationExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}

internal fun Project.configureApplicationKotlinAndroid(
    applicationExtension: ApplicationExtension
) {
    applicationExtension.apply {
        compileSdk = configCompileSdkVersion

        defaultConfig.minSdk = configMinSdkVersion

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }
    configureKotlin()

    dependencies {
    }
}