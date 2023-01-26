object Apps {
    const val compileSdk = 33
    const val minSdk = 24
    const val targetSdk = 33
    const val versionCode = 1
    const val versionName = "0.0.1"

}

object Ver {
    const val hilt = "2.44.2"
}

object Libs {
    const val androidxCore = "androidx.core:core-ktx:1.9.0"
    const val androidxAppcompat = "androidx.appcompat:appcompat:1.6.0"
    const val material = "com.google.android.material:material:1.7.0"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.4"

    const val junit = "junit:junit:4.13.2"
    const val junitExt = "androidx.test.ext:junit:1.1.5"
    const val espresso = "androidx.test.espresso:espresso-core:3.5.1"

    const val hilt = "com.google.dagger:hilt-android:${Ver.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Ver.hilt}"

    const val room ="androidx.room:room-runtime:2.5.0"
    const val roomCompiler = "androidx.room:room-compiler:2.5.0"

    const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:2.9.0"
    const val okhttp = "com.squareup.okhttp3:logging-interceptor:4.10.0"

    const val paging = "androidx.paging:paging-runtime-ktx:3.2.0-alpha03"

    const val glide = "com.github.bumptech.glide:glide:4.14.2"
    const val glideCompiler = "com.github.bumptech.glide:compiler:4.14.2"

    const val activityKtx = "androidx.activity:activity-ktx:1.7.0-alpha03"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:1.6.0-alpha04"

    const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0-alpha04"
}

object TestLibs {
//    const val junit = "junit:junit:${Versions.junit}"
}