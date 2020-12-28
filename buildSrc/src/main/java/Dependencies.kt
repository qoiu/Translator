import org.gradle.api.JavaVersion

object Config{
    const val sdk=30
    const val appName="com.qoiu.translator"
    const val minSdk = 21
    const val targetSdk = 30
    const val versionCode = 1
    const val versionName = "1.0"
    val javaVersion =  JavaVersion.VERSION_1_8
}

object Modules {
    const val app = ":app"
    // Features
    const val core = ":core"
    const val model = ":model"
    const val repository = ":repository"
    const val utils = ":utils"
    const val historyScreen = ":historyScreen"
}

object Versions{
    const val support_lib="27.0.2"
    const val retrofit="2.3.0"
    const val rxjava="2.1.9"
    const val koin="2.0.1"
}

object Base{

    const val core = "androidx.core:core-ktx:1.3.2"
    const val appCompat = "androidx.appcompat:appcompat:1.2.0"
    const val constraintLayout =  "androidx.constraintlayout:constraintlayout:2.0.4"
    const val lifecycleViewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
    const val lefecycleExtensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    const val junit = "junit:junit:4.12"
    const val junitAndroidX = "androidx.test.ext:junit:1.1.2"
    const val testEspresso = "androidx.test.espresso:espresso-core:3.3.0"
    const val androidX = "androidx.appcompat:appcompat:1.0.2"
    const val material = "com.google.android.material:material:1.0.0"
    const val playCore =  "com.google.android.play:core:1.6.3"
}

object Koin{
    const val koin = "org.koin:koin-android:${Versions.koin}"
    const val koinViewModel = "org.koin:koin-android-viewmodel:${Versions.koin}"
}

object Kotlin{
    const val core = "androidx.core:core-ktx:1.0.2"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.41"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.2.1"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.1.1"

}

object Tools {
    const val multidex = "com.android.support:multidex: 1.0.3"
}
// Kotlin

// Rx-Java
object RxJava {
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.0"
    const val rxJava = "io.reactivex.rxjava2:rxjava:2.2.8"
}
object Retrofit {
    const val retrofit =  "com.squareup.retrofit2:retrofit:2.6.0"
    const val converterGson=  "com.squareup.retrofit2:converter-gson:2.6.0"
    const val interceptor =  "com.squareup.okhttp3:logging-interceptor:3.12.1"
    const val coroutineAdapter =  "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
}
object Picasso {
    const val picasso = "com.squareup.picasso:picasso:2.5.2"
}
object Glide {
    const val glide = "com.github.bumptech.glide:glide:4.9.0"
    const val compiler = "com.github.bumptech.glide:compiler:4.9.0"
}

object Room {
    const val room = "androidx.room:room-runtime:2.2.0-alpha01"
    const val compiler = "androidx.room:room-compiler:2.2.0-alpha01"
    const val ktx=  "androidx.room:room-ktx:2.2.0-alpha01"
}
    object Dagger {
    const val dagger =  "com.google.dagger:dagger-android:2.17"
    const val androidSupport =  "com.google.dagger:dagger-android-support:2.17"
    const val androidProcessor = "com.google.dagger:dagger-android-processor:2.17"
    const val compiler = "com.google.dagger:dagger-compiler:2.17"
}
