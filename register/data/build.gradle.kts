plugins {
    alias(libs.plugins.loperilla.library)
}

android {
    namespace = "io.loperilla.register.data"

}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.register.domain)
}