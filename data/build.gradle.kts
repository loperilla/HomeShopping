plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kspPlugin)
    kotlin("android")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "${MyConfiguration.baseProjectName}.data"
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
    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

dependencies {
    implementation(project(MyConfiguration.MAP_MODULES.DOMAIN))

    //Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    //Datastore
    implementation(libs.datastore)
    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)

    // Room
    implementation(libs.room.ktx)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)
    //Datastore
    implementation(libs.datastore)
    implementation(libs.timber)
    //Test
    testImplementation(libs.junit)
    testImplementation(libs.assertk)
    testImplementation(libs.bundles.jupiter)
    testRuntimeOnly(libs.jupiter.engine)
}