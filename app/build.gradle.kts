
plugins {
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    alias(libs.plugins.application)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
    alias((libs.plugins.kotlin))
}

android {
    namespace = "com.example.cleanarchicmoview"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cleanarchicmoview"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField ("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField ("String", "API_KEY", "\"662e2815a88732986f77e82fd5dccde2\"")
        }
        debug {
            buildConfigField ("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField ("String", "API_KEY", "\"662e2815a88732986f77e82fd5dccde2\"")
        }
    }
    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
//        jvmTarget = "1.8"
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
        viewBinding = true
    }
    kapt {
        correctErrorTypes = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    viewBinding {
        enable = true
    }
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.activity)
    implementation(platform(libs.androidx.compose))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation (libs.androidx.fragment)

    // Unit Testing
    testImplementation (libs.mockito)
    testImplementation (libs.truth)
    testImplementation (libs.coroutines.test)

    // Coroutines
    implementation (libs.coroutines.core)
    implementation (libs.coroutines.android)

    // Coroutine Lifecycle Scopes
    implementation (libs.lifecycle.viewmodel)
    implementation (libs.lifecycle.runtime)

    //Dagger - Hilt
    implementation (libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.okhttp)
    implementation(libs.logininterceptor) {
        exclude(group="org.json",module = "json")
    }

    // Glide
    implementation (libs.glide)
    kapt(libs.glide.compiler)

    // Room
    implementation (libs.room.runtime)
    kapt(libs.room.compiler)
    implementation (libs.room.ktx)

//     LeakCanary
    debugImplementation (libs.leakcanary)

//     Chucker
    debugImplementation (libs.chucker.library)
    releaseImplementation (libs.chucker.library.no)

    // Navigation
    implementation (libs.navigation.fragment.ktx)
    implementation (libs.navigation.ui.ktx)

    // Java language implementation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // Feature module Support
    implementation(libs.navigation.dynamic)

    // Testing Navigation
    androidTestImplementation(libs.navigation.testing)

    // Jetpack Compose Integration
    implementation(libs.navigation.compose)
}