plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.yoosangyeop.imagepicker.feature.search"
    compileSdk = Apps.compileSdk

    defaultConfig {
        minSdk = Apps.minSdk
    }

    dataBinding {
        enable = true
    }
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))

    implementation(Libs.androidxCore)
    implementation(Libs.androidxAppcompat)
    implementation(Libs.material)
    implementation(Libs.constraintlayout)
    implementation(project(mapOf("path" to ":core:repository")))
    testImplementation(Libs.junit)
    androidTestImplementation(Libs.junitExt)
    androidTestImplementation(Libs.espresso)

    // Hilt
    implementation(Libs.hilt)
    kapt(Libs.hiltCompiler)

    // room
    implementation(Libs.room)
    annotationProcessor(Libs.roomCompiler)
    kapt(Libs.roomCompiler)

    // Retrofit
    implementation(Libs.retrofit)
    implementation(Libs.gsonConverter)
    implementation(Libs.okhttp)

    // Paging
    implementation(Libs.paging)

    // Glide
    implementation(Libs.glide)
    kapt(Libs.glideCompiler)
    
    // ui controller ktx
    implementation(Libs.activityKtx)
    implementation(Libs.fragmentKtx)

    // ViewModel
    implementation(Libs.viewmodel)
}