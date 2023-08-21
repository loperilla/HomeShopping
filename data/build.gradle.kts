plugins {
    alias(libs.plugins.androidLibrary)
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "${MyConfiguration.baseProjectName}.data"
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
    implementation(project(MyConfiguration.MAP_MODULES.DATASOURCE))
    implementation(project(MyConfiguration.MAP_MODULES.MODEL))
    //Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    //Datastore
    implementation(libs.datastore)
    //Hilt
    implementation(libs.hilt.android)

    kapt(libs.hilt.compiler)
    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
}