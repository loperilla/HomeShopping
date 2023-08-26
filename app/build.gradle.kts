plugins {
    alias(libs.plugins.androidApplication)
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
//    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = MyConfiguration.configCompileSdkVersion
    namespace = MyConfiguration.myApplicationIdConfig

    defaultConfig {
        applicationId = MyConfiguration.myApplicationIdConfig
        minSdk = MyConfiguration.configMinSdkVersion
        targetSdk = MyConfiguration.configTargetSdkVersion
        versionCode = MyConfiguration.configTargetSdkVersion
        versionName = MyConfiguration.configVersionName
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {

        }
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
    implementation(libs.androidx.activity)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(project(MyConfiguration.Modules.COREUI))
    implementation(project(MyConfiguration.Modules.ONB_DOMAIN))
    implementation(project(MyConfiguration.Modules.ONB_PRESENTATION))
    implementation(project(MyConfiguration.MAP_MODULES.MODEL))

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.hilt.navigation)
    implementation(libs.splashscreen)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

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
}