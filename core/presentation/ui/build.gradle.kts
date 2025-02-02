plugins {
    alias(libs.plugins.loperilla.library.compose)
}

android {
    namespace = "io.loperilla.core.presentation.ui"
}

dependencies {
    implementation(libs.bundles.compose)
    implementation(projects.core.domain)
}