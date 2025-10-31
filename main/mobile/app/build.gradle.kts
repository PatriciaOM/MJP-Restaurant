plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.dokka") version "1.9.10"
}

android {
    namespace = "com.example.mjprestaurant"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.mjprestaurant"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

// ✅ DOKKA FUERA DE DEPENDENCIES
tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
    outputDirectory.set(file("$buildDir/dokka"))
    moduleName.set("MJPRestaurant")
}

// ✅ TAREA CORREGIDA FUERA DE DEPENDENCIES
tasks.register("generateDocs") {
    group = "documentation"
    description = "Genera documentación con Dokka"
    dependsOn(tasks.named("dokkaHtml"))
}

dependencies {
    // ✅ ANDROIDX Y COMPOSE
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation("androidx.navigation:navigation-compose:2.7.3")

    // ✅ VIEWMODEL + COROUTINES
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

    // ✅ RETROFIT + OKHTTP
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // ✅ TESTING UNITARIO
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.9.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("com.google.truth:truth:1.1.3")
    testImplementation("io.mockk:mockk:1.13.5")

    // ✅ ANDROID TEST
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")

    // ✅ DEBUG
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}