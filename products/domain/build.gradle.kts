plugins {
    alias(libs.plugins.loperilla.jvm.library)
}

dependencies {
    implementation(projects.core.domain)
}