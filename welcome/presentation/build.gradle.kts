plugins {
    alias(libs.plugins.loperilla.feature.ui)
    alias(libs.plugins.loperilla.library)
}

android {
    namespace = "io.loperilla.welcome.presentation"
}

dependencies {
    implementation(projects.core.domain)
}