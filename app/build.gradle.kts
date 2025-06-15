plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.wallpaper"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.wallpaper"
        minSdk = 27
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.animation)



    implementation(libs.androidx.material.icons.core) // Or the latest version
    implementation(libs.androidx.material.icons.extended) // For a wider range of icons

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.lifecycle.viewmodel.compose) // ViewModel support for Compose[8][6]
    implementation(libs.androidx.lifecycle.runtime.compose)   // Lifecycle integration for Compose[8]
    implementation(libs.androidx.navigation.compose)         // Navigation for Compose
    implementation(libs.coil.compose)    // Image loading for Compose

    ksp(libs.androidx.room.compiler)                               // Room annotation processor
    implementation(libs.androidx.room.runtime)                     // Room runtime
    implementation(libs.androidx.room.ktx)                         // Kotlin extensions for Room
    implementation(libs.androidx.room.paging) // Paging support for Room

    implementation(libs.androidx.core.splashscreen)

    implementation(libs.cloudy)

    implementation(libs.androidx.paging.runtime.ktx)             // Paging runtime for Kotlin
    implementation(libs.paging.compose)                 // Paging integration for Compose

    implementation(libs.kotlinx.serialization.json) // JSON serialization

    implementation(libs.retrofit)               // Retrofit core
    implementation(libs.converter.gson)         // Gson converter
    implementation(libs.retrofit2.converter.scalars)      // Scalars converter
    implementation(libs.retrofit2.kotlinx.serialization.converter) // Kotlinx serialization converter
    implementation(libs.okhttp)                   // OkHttp client


    implementation(libs.hilt.android)                  // Hilt core
    ksp(libs.dagger.hilt.android.compiler)                    // Hilt annotation processor
    ksp(libs.androidx.hilt.compiler)                               // Hilt compiler for Jetpack integration
    implementation(libs.hilt.navigation.compose)          // Hilt navigation support for Compose[9]
    implementation( libs.bottombar)
}