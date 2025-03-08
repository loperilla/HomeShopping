plugins {
    alias(libs.plugins.loperilla.feature.ui)
}

android {
    namespace = "io.loperilla.welcome.presentation"
}

dependencies {
    implementation(projects.core.domain)
}