plugins {
    alias(libs.plugins.androidLibrary)
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "${MyConfiguration.baseProjectName}.datasource"
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

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(MyConfiguration.MAP_MODULES.MODEL))

    //Datastore
    implementation(libs.datastore)
    //Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
}