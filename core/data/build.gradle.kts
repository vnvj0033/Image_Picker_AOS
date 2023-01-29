plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.yoosangyeop.imagepicker.core.data"
    compileSdk = Apps.compileSdk

    defaultConfig {
        minSdk = Apps.minSdk
    }
}

dependencies {
    implementation(project(":core:model"))

    testImplementation(Libs.junit)
    androidTestImplementation(Libs.junitExt)
    androidTestImplementation(Libs.espresso)

    // room
    implementation(Libs.room)
    annotationProcessor(Libs.roomCompiler)
    kapt(Libs.roomCompiler)

    // Retrofit
    implementation(Libs.retrofit)
    implementation(Libs.gsonConverter)
    implementation(Libs.okhttp)

    // Hilt
    implementation(Libs.hilt)
    kapt(Libs.hiltCompiler)
}