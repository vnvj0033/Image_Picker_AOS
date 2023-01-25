plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.yoosangyeop.imagepicker.model"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
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