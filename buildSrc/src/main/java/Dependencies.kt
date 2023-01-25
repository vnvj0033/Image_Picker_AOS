object Apps {

}

object Versions {
//    const val gradle = "3.5.2"
//    const val kotlin = "1.3.50"
//    const val appcompat = "1.0.2"
//
//    const val junit = "4.12"
}

object Libs {
    const val androidxCore = "androidx.core:core-ktx:1.9.0"
    const val androidxAppcompat = "androidx.appcompat:appcompat:1.6.0"
    const val material = "com.google.android.material:material:1.7.0"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.4"

    const val junit = "junit:junit:4.13.2"
    const val junitExt = "androidx.test.ext:junit:1.1.5"
    const val espresso = "androidx.test.espresso:espresso-core:3.5.1"

    const val hilt = "com.google.dagger:hilt-android:2.44.2"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:2.44.2"

}

object TestLibs {
//    const val junit = "junit:junit:${Versions.junit}"
}

internal val composeOfficialDependencies = listOf(
    Libs.androidxCore,
    Libs.androidxAppcompat
)