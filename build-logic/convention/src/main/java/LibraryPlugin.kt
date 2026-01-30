import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.LibraryExtension
import io.loperilla.convention.utils.configCompileSdkVersion
import io.loperilla.convention.utils.configMinSdkVersion
import io.loperilla.convention.utils.configureKotlin
import io.loperilla.convention.utils.libs
import org.gradle.api.JavaVersion
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
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            extensions.configure<LibraryExtension> {
                configureLibraryKotlin(this)

                configureLibraryBuildType(
                    this
                )

                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
            }

            dependencies {
                "implementation"(target.libs.findBundle("koin").get())
            }
        }
    }
}

internal fun Project.configureLibraryBuildType(
    libraryExtension: LibraryExtension
) {
    libraryExtension.run {
        buildFeatures {
            buildConfig = true
        }
        buildTypes {
            debug {
            }
            release {
                configureLibraryReleaseBuildType(libraryExtension)
            }
        }
    }
}

internal fun BuildType.configureLibraryReleaseBuildType(
    libraryExtension: LibraryExtension
) {
    isMinifyEnabled = true
    proguardFiles(
        libraryExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}

internal fun Project.configureLibraryKotlin(
    libraryExtension: LibraryExtension
) {
    libraryExtension.run {
        compileSdk = configCompileSdkVersion

        defaultConfig.minSdk = configMinSdkVersion

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        configureKotlin()

        dependencies {
        }
    }
}