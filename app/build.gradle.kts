plugins {
    alias(libs.plugins.loperilla.application.compose)
}

android {
    namespace = "io.loperilla.homeshopping"
}

composeCompiler {
    enableStrongSkippingMode = true

    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    //stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
}

dependencies {
    implementation(libs.androidx.activity)
    implementation(libs.lifecycle.runtime.ktx)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.bundles.koin.compose)
    implementation(libs.splashscreen)
    implementation(libs.timber)


    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)

    testImplementation(libs.junit)
    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.test.espresso)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

    // project
    core()
    welcome()
    register()
    login()
}


fun DependencyHandlerScope.welcome() {
    implementation(projects.welcome.presentation)
}

fun DependencyHandlerScope.login() {
    implementation(projects.login.data)
    implementation(projects.login.domain)
    implementation(projects.login.presentation)
}

fun DependencyHandlerScope.register() {
    implementation(projects.register.data)
    implementation(projects.register.domain)
    implementation(projects.register.presentation)
}

fun DependencyHandlerScope.core() {
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.presentation.ui)
    implementation(projects.core.presentation.designsystem)
}
