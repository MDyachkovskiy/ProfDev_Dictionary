plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "gb.com"
    compileSdk = Config.compile_sdk

    defaultConfig {
        applicationId = Config.application_id
        minSdk = Config.min_sdk
        targetSdk = Config.target_sdk
        versionCode = Releases.version_code
        versionName = Releases.version_name

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    //AndroidX
    implementation(Design.appcompat)
    implementation(Kotlin.runtime_ktx)

    //Design
    implementation(Design.material)
    implementation(Design.constraintlayout)

    //Kotlin
    implementation(Kotlin.core)

    //Retrofit
    implementation(Retrofit.retrofit)
    implementation(Retrofit.converter_gson)
    implementation(Retrofit.adapter_rxjava3)
    implementation(Retrofit.adapter_rxjava2)
    implementation(Retrofit.interceptor)

    //RxJava
    implementation(RxJava.rxandroid)
    implementation(RxJava.rxjava)

    //Koin
    implementation(Koin.koin_core)
    implementation(Koin.koin_android)
    implementation(Koin.koin_android_compat)

    //Glide
    implementation(Glide.glide)
    implementation("com.google.ar:core:1.30.0")
    kapt(Glide.compiler)


    //Coroutines
    implementation(Coroutines.coroutinesCore)
    implementation(Coroutines.coroutinesAndroid)
    implementation(Coroutines.retrofitAdapter)

    //Room
    implementation(Room.room_runtime)
    implementation(Room.room_ktx)
    kapt(Room.room_compiler)

    //Test
    testImplementation(Test.junit)
    androidTestImplementation(Test.ext_junit)
    androidTestImplementation(Test.espresso_core)
}