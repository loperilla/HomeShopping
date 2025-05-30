plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kspPlugin)
    alias(libs.plugins.androidJUnit5)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    kotlin("android")
}

android {
    namespace = "io.loperilla.onboarding_domain"
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

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.kotlinx.serialization.json)

    //Runtime
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.timber)


    //Test
    testImplementation(libs.junit)
    testImplementation(libs.bundles.jupiter)
    testImplementation(libs.coroutine.test)
    testImplementation(libs.mockk)
    testImplementation(libs.assertk)
    testRuntimeOnly(libs.jupiter.engine)
}