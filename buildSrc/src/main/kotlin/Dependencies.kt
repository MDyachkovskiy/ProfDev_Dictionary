object Config {
    const val application_id = "gb.com"
    const val compile_sdk = 33
    const val min_sdk = 28
    const val target_sdk = 33
}

object Releases {
    const val version_code = 1
    const val version_name = "1.0"
}

object Modules {
    const val app = ":app"
    const val core = ":core"
    const val model = ":model"
    const val repository = ":repository"
    const val utils = ":utils"

    //Features
    const val historyScreen = ":historyScreen"
}

object Versions {
    //Design
    const val appcompat = "1.6.1" //actual February 8, 2023
    const val constraintlayout = "2.1.4" // actual May 20, 2022
    const val material = "1.9.0" // actual May 5, 2023

    //Kotlin
    const val core = "1.10.1" // actual May 10, 2023
    const val runtime_ktx = "2.6.1" // actual March 22, 2023

    //Coroutines
    const val coroutinesCore = "1.7.2" // actual Jun 29, 2023
    const val coroutinesAndroid = "1.7.2" // actual Jun 29, 2023
    const val retrofitAdapter = "0.9.2"

    //Retrofit
    const val retrofit = "2.9.0" // actual May 20, 2020
    const val converter_gson = "2.9.0" // actual May 20, 2020
    const val adapter_rxjava3 = "2.9.0" // actual May 20, 2020
    const val adapter_rxjava2 = "2.9.0" // actual May 20, 2020
    const val interceptor = "4.11.0" // actual Apr 23, 2023

    //RxJava
    const val rxandroid = "2.1.1" //actual Feb 16, 2019
    const val rxjava = "2.2.21" //actual Feb 13, 2021

    //Koin
    const val koin_core = "3.4.2" // actual Jun 05, 2023
    const val koin_android = "3.4.2" // actual Jun 05, 2023
    const val koin_android_compat = "3.4.2" // actual Jun 05, 2023

    //Glide
    const val glide = "4.15.1" // actual Mar 14 2023
    const val compiler = "4.15.1" // actual Mar 14 2023

    //Room
    const val room_runtime = "2.5.2" // actual Jun 21, 2023
    const val room_ktx = "2.5.2" // actual Jun 21, 2023
    const val room_compiler = "2.5.2" // actual Jun 21, 2023

    //Test
    const val junit = "4.13.2"
    const val ext_junit = "1.1.5"
    const val espresso_core = "3.5.1"
}

object Design {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val material = "com.google.android.material:material:${Versions.material}"
}

object Kotlin {
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.runtime_ktx}"
}

object Coroutines {
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
    const val retrofitAdapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitAdapter}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.converter_gson}"
    const val adapter_rxjava3 = "com.squareup.retrofit2:adapter-rxjava3:${Versions.adapter_rxjava3}"
    const val adapter_rxjava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.adapter_rxjava2}"
    const val interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.interceptor}"
}


object RxJava {
    const val rxandroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxandroid}"
    const val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
}


object Koin {
    const val koin_core = "io.insert-koin:koin-core:${Versions.koin_core}"
    const val koin_android = "io.insert-koin:koin-android:${Versions.koin_android}"
    const val koin_android_compat = "io.insert-koin:koin-android-compat:${Versions.koin_android_compat}"
}


object Glide {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val compiler = "com.github.bumptech.glide:compiler:${Versions.compiler}"
}


object Room {
    const val room_runtime = "androidx.room:room-runtime:${Versions.room_runtime}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room_ktx}"
    const val room_compiler = "androidx.room:room-compiler:${Versions.room_compiler}"
}


object Test {
    const val junit = "junit:junit:${Versions.junit}"
    const val ext_junit = "androidx.test.ext:junit:${Versions.ext_junit}"
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"
}
