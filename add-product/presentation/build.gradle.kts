plugins {
    alias(libs.plugins.loperilla.feature.ui)
}

android {
    namespace = "io.loperilla.add_product.presentation"
}

dependencies {
    implementation(projects.core.domain)
}