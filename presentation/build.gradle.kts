plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kspPlugin)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "io.loperilla.onboarding_presentation"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

composeCompiler {
    enableStrongSkippingMode = true

    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    //stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
}

dependencies {

    //Runtime
    implementation(libs.lifecycle.runtime.ktx)

    //Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.bundles.accompanist)
    implementation(libs.timber)

    // Camera
    implementation(libs.bundles.camera)


    implementation(libs.kotlinx.serialization.json)


    //Test
    testImplementation(libs.junit)
    testImplementation(libs.bundles.jupiter)
    testImplementation(libs.coroutine.test)
    testImplementation(libs.mockk)
    testImplementation(libs.assertk)
    testImplementation(libs.turbine)
    testRuntimeOnly(libs.jupiter.engine)

    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.test.espresso)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
}