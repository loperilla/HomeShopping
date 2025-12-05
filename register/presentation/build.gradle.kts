plugins {
    alias(libs.plugins.loperilla.feature.ui)
    alias(libs.plugins.loperilla.library)
}

android {
    namespace = "io.loperilla.register.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.register.domain)
}