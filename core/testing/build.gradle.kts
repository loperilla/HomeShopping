plugins {
    alias(libs.plugins.loperilla.library.compose)
}

android {
    namespace = "io.loperilla.core.testing"
}

dependencies {
    implementation(projects.core.domain)
    api(libs.junit)
    api(libs.mockk)
    api(libs.kotlinx.coroutines.test)
    api(libs.turbine)
}