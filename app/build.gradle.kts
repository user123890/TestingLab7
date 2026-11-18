
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.gradle.api.tasks.testing.Test

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.3.8"
    id("jacoco")
}

android {
    namespace = "com.example.testinglab7"

    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.testinglab7"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            enableUnitTestCoverage = true
        }
        create("benchmark") {
            initWith(buildTypes.getByName("release"))
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }

        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
        animationsDisabled = true

        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    // Основные библиотеки
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // JUnit
    testImplementation(libs.junit)

    // Mockito
    testImplementation("org.mockito:mockito-core:5.18.0")

    // Robolectric
    testImplementation("org.robolectric:robolectric:4.17")

    // Room
    implementation("androidx.room:room-runtime:2.8.5")
    implementation("androidx.room:room-ktx:2.8.5")
    ksp("androidx.room:room-compiler:2.8.5")

    // Room testing
    testImplementation("androidx.room:room-testing:2.8.5")
    androidTestImplementation("androidx.room:room-testing:2.8.5")

    // MockWebServer
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    testImplementation("com.squareup.okhttp3:okhttp:4.12.0")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    androidTestImplementation("com.squareup.okhttp3:okhttp:4.12.0")

    // UI tests
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation("androidx.test:core:1.6.1")

    // Android Test Orchestrator
    androidTestUtil("androidx.test:orchestrator:1.5.1")

    // Debug
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Задание 13: LeakCanary для обнаружения утечек памяти
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.14")
}

// Временно исключаем проблемные Robolectric-тесты
tasks.withType<Test>().configureEach {
    exclude("**/RobolectricTest.class")
}

// JaCoCo: генерация отчёта покрытия
tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }

    val excludedFiles = listOf(
        "**/R.class",
        "**/R\$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*"
    )

    // Kotlin-классы
    val kotlinClasses = fileTree(
        "${layout.buildDirectory.get()}/intermediates/built_in_kotlinc/debug/compileDebugKotlin/classes"
    ) {
        exclude(excludedFiles)
    }

    // Java-классы
    val javaClasses = fileTree(
        "${layout.buildDirectory.get()}/intermediates/javac/debug/compileDebugJavaWithJavac/classes"
    ) {
        exclude(excludedFiles)
    }

    classDirectories.setFrom(
        files(kotlinClasses, javaClasses)
    )

    sourceDirectories.setFrom(
        files("src/main/java", "src/main/kotlin")
    )

    executionData.setFrom(
        fileTree(layout.buildDirectory) {
            include("**/*.exec")
        }
    )
}
