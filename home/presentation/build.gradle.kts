plugins {
    alias(libs.plugins.loperilla.feature.ui)
}

android {
    namespace = "io.loperilla.home.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.home.domain)
}