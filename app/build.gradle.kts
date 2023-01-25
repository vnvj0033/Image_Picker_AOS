@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.yoosangyeop.imagepicker"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.yoosangyeop.imagepicker"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    dataBinding {
        enable = true
    }
    buildToolsVersion = "33.0.0"
}

dependencies {
    implementation(project(":feature:search"))
    implementation(project(":core:model"))

    implementation(Libs.androidxCore)
    implementation(Libs.androidxAppcompat)
    implementation(Libs.material)
    implementation(Libs.constraintlayout)
    testImplementation(Libs.junit)
    androidTestImplementation(Libs.junitExt)
    androidTestImplementation(Libs.espresso)

    // Hilt
    implementation(Libs.hilt)
    kapt(Libs.hiltCompiler)
}