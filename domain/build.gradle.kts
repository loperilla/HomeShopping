plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kspPlugin)
    alias(libs.plugins.androidJUnit5)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    kotlin("android")
}

android {
    namespace = "${MyConfiguration.baseProjectName}.onboarding_domain"
    compileSdk = MyConfiguration.configCompileSdkVersion

    defaultConfig {
        minSdk = MyConfiguration.configMinSdkVersion

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

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.kotlinx.serialization.json)

    //Runtime
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.timber)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    //Test
    testImplementation(libs.junit)
    testImplementation(libs.bundles.jupiter)
    testImplementation(libs.coroutine.test)
    testImplementation(project(mapOf("path" to ":testutils")))
    testImplementation(libs.mockk)
    testImplementation(libs.assertk)
    testRuntimeOnly(libs.jupiter.engine)
}