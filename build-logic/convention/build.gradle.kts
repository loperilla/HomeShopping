plugins {
    `kotlin-dsl`
}
group = "io.loperilla.homeshopping.buildlogic"

dependencies {
    compileOnly(libs.build.gradle)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = libs.plugins.loperilla.application.asProvider().get().pluginId
            implementationClass = "AndroidApplicationPlugin"
        }
        register("androidApplicationCompose") {
            id = libs.plugins.loperilla.application.compose.get().pluginId
            implementationClass = "ComposeApplicationPlugin"
        }
        register("androidFeatureUi") {
            id = libs.plugins.loperilla.feature.ui.get().pluginId
            implementationClass = "UiFeaturePlugin"
        }
        register("androidLibrary") {
            id = libs.plugins.loperilla.library.asProvider().get().pluginId
            implementationClass = "LibraryPlugin"
        }
        register("androidLibraryCompose") {
            id = libs.plugins.loperilla.library.compose.get().pluginId
            implementationClass = "ComposeLibraryPlugin"
        }
        register("jvmLibrary") {
            id = libs.plugins.loperilla.jvm.library.get().pluginId
            implementationClass = "JvmLibraryPlugin"
        }
    }
}
