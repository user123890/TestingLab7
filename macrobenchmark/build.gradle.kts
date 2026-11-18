
plugins {
    alias(libs.plugins.android.test)
}

android {
    namespace = "com.example.testinglab7.macrobenchmark"

    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 23
        targetSdk = 37
        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        create("benchmark") {
            isDebuggable = true
            signingConfig = getByName("debug").signingConfig
            matchingFallbacks += listOf("release")
        }
    }

    targetProjectPath = ":app"

    experimentalProperties[
        "android.experimental.self-instrumenting"
    ] = true
}

dependencies {
    implementation("androidx.benchmark:benchmark-macro-junit4:1.4.1")
    implementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation("androidx.test.ext:junit:1.2.1")
    implementation("androidx.test.uiautomator:uiautomator:2.3.0")
}

androidComponents {
    beforeVariants(selector().all()) {
        it.enable = it.buildType == "benchmark"
    }
}
