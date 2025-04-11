plugins {
    alias(libs.plugins.loperilla.feature.ui)
}

android {
    namespace = "io.loperilla.commerce.presentation"
}

dependencies {
    implementation(projects.core.domain)
}