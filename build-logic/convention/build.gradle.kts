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
            id = "homeshopping.android.application"
            implementationClass = "AndroidApplicationPlugin"
        }
        register("androidApplicationCompose") {
            id = "homeshopping.application.compose"
            implementationClass = "ComposeApplicationPlugin"
        }
        register("androidFeatureUi") {
            id = "homeshopping.feature.ui"
            implementationClass = "UiFeaturePlugin"
        }
        register("androidLibrary") {
            id = "homeshopping.android.library"
            implementationClass = "LibraryPlugin"
        }
        register("androidLibraryCompose") {
            id = "homeshopping.library.compose"
            implementationClass = "ComposeLibraryPlugin"
        }
        register("jvmLibrary") {
            id = "homeshopping.jvm.library"
            implementationClass = "JvmLibraryPlugin"
        }
    }
}
