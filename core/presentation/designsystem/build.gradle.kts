plugins {
    alias(libs.plugins.loperilla.library.compose)
}

android {
    namespace = "io.loperilla.presentation.designsystem"

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.coil)
    api(libs.compose.material3)
}