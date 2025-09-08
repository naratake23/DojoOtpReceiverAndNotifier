plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")

}

android {
    namespace = "com.liberty.dojontkotpreceiverandnotifier"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.liberty.dojontkotpreceiverandnotifier"
        minSdk = 30
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
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    // --- Kotlin coroutines (core concurrency for ViewModel/WorkManager) ---
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0") // unit tests

    // --- AndroidX core + Lifecycle (ViewModel/UDF basics) ---
    implementation("androidx.core:core-ktx:1.13.1") // NotificationCompat, etc.
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4") // use hiltViewModel() nicely

    // --- Activity + Compose BOM (UI) ---
    implementation("androidx.activity:activity-compose:1.9.2")

    // Use the Compose BOM to pin versions consistently
    implementation(platform("androidx.compose:compose-bom:2025.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    debugImplementation("androidx.compose.ui:ui-tooling") // inspector in debug builds

    // --- Navigation-Compose (for Home -> Detail + deep links) ---
    implementation("androidx.navigation:navigation-compose:2.9.3")

    // --- Hilt (DI runtime + compiler via KSP) ---
    implementation("com.google.dagger:hilt-android:2.57")
    ksp("com.google.dagger:hilt-compiler:2.57")

    // Hilt helpers for Compose & WorkManager
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0") // hiltViewModel()
    implementation("androidx.hilt:hilt-work:1.2.0")                // @HiltWorker support
    ksp("androidx.hilt:hilt-compiler:1.2.0")                       // required when using hilt-work

    // --- WorkManager (to post the notification from background) ---
    implementation("androidx.work:work-runtime-ktx:2.10.3")
    androidTestImplementation("androidx.work:work-testing:2.10.3")  // run workers in tests

    // --- DataStore (save the user note we’ll show in the notification) ---
    implementation("androidx.datastore:datastore-preferences:1.1.7")

    // --- Testing (unit + instrumented + compose UI) ---
    testImplementation("junit:junit:4.13.2")
    testImplementation("app.cash.turbine:turbine:1.2.1") // Flow testing helper (optional but handy)

    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // Compose UI testing powered by the same BOM
    androidTestImplementation(platform("androidx.compose:compose-bom:2025.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //gives ProcessLifecycleOwner, which lets observe when the whole app goes foreground/background
    implementation("androidx.lifecycle:lifecycle-process:2.9.2")




    testImplementation("org.robolectric:robolectric:4.12.2")
    testImplementation(kotlin("test"))


    testImplementation("org.mockito:mockito-core:5.12.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    testImplementation("org.mockito:mockito-inline:5.2.0") // enables final-class mocking

    testImplementation("com.google.dagger:hilt-android-testing:2.57")
    kspTest("com.google.dagger:hilt-compiler:2.57")

}
