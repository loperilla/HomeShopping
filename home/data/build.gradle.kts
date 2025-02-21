plugins {
    alias(libs.plugins.loperilla.library)
}

android {
    namespace = "io.loperilla.login.data"

}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.home.domain)
}