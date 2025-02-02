plugins {
    alias(libs.plugins.loperilla.library)
}

android {
    namespace = "io.loperilla.core.data"
}

dependencies {
    implementation(libs.bundles.koin)
    // Room
    implementation(libs.room.ktx)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)
    implementation(projects.core.domain)
}