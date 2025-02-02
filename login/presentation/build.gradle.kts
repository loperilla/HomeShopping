plugins {
    alias(libs.plugins.loperilla.feature.ui)
}

android {
    namespace = "io.loperilla.login.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.login.domain)
}