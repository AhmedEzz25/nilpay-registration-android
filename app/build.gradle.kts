plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.0"
    id("kotlin-kapt")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.nilpay_registration_android"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.nilpay_registration_android"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.datastore.core.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Compose dependencies
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    debugImplementation(libs.androidx.ui.tooling)


    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Retrofit + Gson
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.gson)
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")


    // Lifecycle
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.compose)
    // hilt
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    // camera and barcode scan
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.barcode.scanning)
    implementation(libs.kotlinx.serialization.json)

//    room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.common)


//    encrypted sharedprefs

    implementation("androidx.security:security-crypto:1.1.0-alpha07")

    // Moshi core
    implementation("com.squareup.moshi:moshi:1.15.0")

    // Moshi Kotlin support (required for Kotlin data classes)
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")

    // Moshi codegen (optional, only if you use @JsonClass(generateAdapter = true))
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")

    // Retrofit Moshi Converter
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
// Media Picker
    implementation("androidx.activity:activity-compose:1.8.0")

    // ML Kit Barcode Scanning
    implementation("com.google.mlkit:barcode-scanning:17.2.0")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.datastore:datastore:1.0.0")
    implementation ("com.amazonaws:aws-android-sdk-s3:2.16.2")
    implementation ("com.amazonaws:aws-android-sdk-core:2.16.2")

}