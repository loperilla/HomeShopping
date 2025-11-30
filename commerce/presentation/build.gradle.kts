plugins {
    alias(libs.plugins.loperilla.feature.ui)
    alias(libs.plugins.loperilla.library)
}

android {
    namespace = "io.loperilla.commerce.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.commerce.domain)
}