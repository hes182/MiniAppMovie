pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url="https://jitpack.io" )
    }
    versionCatalogs {
        create("libs") {
            /** Version **/
            version("nav", "2.7.2")

            /** Plugins **/
            plugin("application", "com.android.application").version("8.1.1")
            plugin("kotlin", "org.jetbrains.kotlin.android").version("1.8.10")
            plugin("ksp", "com.google.devtools.ksp").version("1.8.10-1.0.9")
            plugin("ktlint", "org.jlleitschuh.gradle.ktlint").version("11.5.1")

            /** Libraries **/
            library("hilt-dagger", "com.google.dagger:hilt-android-gradle-plugin:2.45")

            library("androidx-core", "androidx.core:core-ktx:1.9.0")
            library("androidx-lifecycle", "androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
            library("androidx-activity", "androidx.activity:activity-compose:1.7.2")
            library("androidx-compose", "androidx.compose:compose-bom:2023.03.00")
            library("androidx-appcompat", "androidx.appcompat:appcompat:1.6.1")
            library("androidx-material", "com.google.android.material:material:1.9.0")
            library("androidx-constraintlayout", "androidx.constraintlayout:constraintlayout:2.1.4")
            library("androidx-livedata", "androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
            library("junit", "junit:junit:4.13.2")
            library("androidx-test-junit", "androidx.test.ext:junit:1.1.5")
            library("androidx-test-espresso", "androidx.test.espresso:espresso-core:3.5.1")
            library ("androidx-fragment", "androidx.fragment:fragment-ktx:1.6.1")

            // Unit Testing
            library ("mockito", "org.mockito.kotlin:mockito-kotlin:4.1.0")
            library ("truth", "com.google.truth:truth:1.1.3")
            library ("coroutines-test", "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

            // Coroutines
            library ("coroutines-core", "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
            library ("coroutines-android", "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

            // Coroutine Lifecycle Scopes
            library ("lifecycle-viewmodel", "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
            library ("lifecycle-runtime", "androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

            //Dagger - Hilt
            library ("hilt-android", "com.google.dagger:hilt-android:2.45")
            library("hilt-compiler", "com.google.dagger:hilt-android-compiler:2.45")

            // Retrofit
            library ("retrofit", "com.squareup.retrofit2:retrofit:2.9.0")
            library ("converter-gson", "com.squareup.retrofit2:converter-gson:2.9.0")
            library ("okhttp", "com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
            library("logininterceptor", "com.github.ihsanbal:LoggingInterceptor:3.1.0")

            // Glide
            library ("glide", "com.github.bumptech.glide:glide:4.14.2")
            library("glide-compiler", "com.github.bumptech.glide:compiler:4.14.2")

            // Room
            library ("room-runtime", "androidx.room:room-runtime:2.5.2")
            library("room-compiler", "androidx.room:room-compiler:2.5.2")
            library ("room-ktx", "androidx.room:room-ktx:2.5.2")

            //LeakCanary
            library ("leakcanary", "com.squareup.leakcanary:leakcanary-android:2.10")

            //Chucker
            library ("chucker-library", "com.github.chuckerteam.chucker:library:3.5.2")
            library ("chucker-library-no", "com.github.chuckerteam.chucker:library-no-op:3.5.2")

            // Navigation
            library ("navigation-fragment-ktx", "androidx.navigation:navigation-fragment-ktx:2.7.2")
            library ("navigation-ui-ktx", "androidx.navigation:navigation-ui-ktx:2.7.2")

            // Java language library
            library("navigation-fragment", "androidx.navigation", "navigation-fragment").versionRef("nav")
            library("navigation-ui", "androidx.navigation", "navigation-ui").versionRef("nav")

            // Feature module Support
            library("navigation-dynamic", "androidx.navigation", "navigation-dynamic-features-fragment").versionRef("nav")

            // Testing Navigation
            library("navigation-testing", "androidx.navigation","navigation-testing").versionRef("nav")

            // Jetpack Compose Integration
            library("navigation-compose", "androidx.navigation", "navigation-compose").versionRef("nav")
        }
    }
}

rootProject.name = "CleanArchicMoview"
include(":app")
