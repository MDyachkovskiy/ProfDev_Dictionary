import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.android.build.gradle.BaseExtension

plugins {
    id ("com.android.application") version "8.0.1" apply false
    id ("com.android.library") version "8.0.1" apply false
    id ("org.jetbrains.kotlin.android") version "1.7.0" apply false
    id ("org.jetbrains.kotlin.jvm") version "1.7.0"
}
repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

tasks.withType<KotlinCompile>().configureEach{
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

subprojects {
    afterEvaluate {
        val android = project.extensions.findByName("android") as? BaseExtension
        if (android != null) {
            android.run {
                compileSdkVersion(Config.compile_sdk)
                buildFeatures.viewBinding = true
                defaultConfig {
                    minSdkVersion(Config.min_sdk)
                    targetSdkVersion(Config.target_sdk)
                }
            }
        }
    }
}