plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.yoosangyeop.imagepicker.model"
    compileSdk = Apps.compileSdk

    defaultConfig {
        minSdk = Apps.minSdk
    }
}

dependencies {

    // room
    implementation(Libs.room)
    annotationProcessor(Libs.roomCompiler)
    kapt(Libs.roomCompiler)

    // Retrofit
    implementation(Libs.retrofit)
    implementation(Libs.gsonConverter)
    implementation(Libs.okhttp)
}