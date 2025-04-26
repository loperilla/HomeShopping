plugins {
    alias(libs.plugins.loperilla.feature.ui)
}

android {
    namespace = "io.loperilla.products.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.products.domain)
}