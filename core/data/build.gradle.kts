import java.util.Properties

plugins {
    alias(libs.plugins.loperilla.library)
}

android {
    namespace = "io.loperilla.core.data"
    buildTypes {
        debug {
            val keystoreFile = project.rootProject.file("local.properties")
            val properties = Properties().apply {
                load(keystoreFile.inputStream())
            }
            buildConfigField("String", "GOOGLE_CLIENT_ID", properties.getProperty("GoogleWebClientId"))
        }
    }
    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.bundles.koin)
    // Room
    implementation(libs.room.ktx)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)

    implementation(libs.google.auth)
    implementation(libs.google.identity)
    implementation(libs.androidx.credentials.core)
    implementation(libs.androidx.credentials.compat)
    implementation(projects.core.domain)
}