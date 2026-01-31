plugins {
    alias(libs.plugins.loperilla.library.compose)
}

android {
    namespace = "io.loperilla.core.presentation.ui"
}

dependencies {
    implementation(libs.bundles.compose)
    implementation(libs.bundles.koin)
    implementation(projects.core.domain)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.navigation3.runtime)
    implementation(libs.navigation3.ui)
}