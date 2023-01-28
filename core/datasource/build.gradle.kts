plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.yoosangyeop.imagepicker.core.datasource"
    compileSdk = Apps.compileSdk

    defaultConfig {
        minSdk = Apps.minSdk
    }
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))

    implementation(Libs.androidxCore)
    implementation(Libs.androidxAppcompat)
    implementation(Libs.material)
    testImplementation(Libs.junit)
    androidTestImplementation(Libs.junitExt)
    androidTestImplementation(Libs.espresso)

    // Hilt
    implementation(Libs.hilt)
    kapt(Libs.hiltCompiler)
}