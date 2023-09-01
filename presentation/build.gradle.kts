plugins {
    alias(libs.plugins.androidLibrary)
    kotlin("android")
    alias(libs.plugins.android.junit)
    alias(libs.plugins.kspPlugin)
}

android {
    namespace = "${MyConfiguration.baseProjectName}.onboarding_presentation"
    compileSdk = MyConfiguration.configCompileSdkVersion

    defaultConfig {
        minSdk = MyConfiguration.configMinSdkVersion
        targetSdk = MyConfiguration.configTargetSdkVersion

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
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(MyConfiguration.Modules.COREUI))
    implementation(project(MyConfiguration.MAP_MODULES.DOMAIN))
    implementation(project(MyConfiguration.MAP_MODULES.MODEL))

    //Runtime
    implementation(libs.lifecycle.runtime.ktx)

    //Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.hilt.navigation)
    implementation(libs.timber)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    //Test
    testImplementation(libs.junit)
    testImplementation(libs.assertk)
    testImplementation(libs.bundles.jupiter)
    testRuntimeOnly(libs.jupiter.engine)

    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.test.espresso)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
}