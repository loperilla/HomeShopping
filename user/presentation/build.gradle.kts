plugins {
    alias(libs.plugins.loperilla.feature.ui)
}

android {
    namespace = "io.loperilla.user.presentation"
}

dependencies {
    implementation(projects.core.domain)
}