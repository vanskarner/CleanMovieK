import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
}

android {
    namespace = "com.vanskarner.movie"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val dataProperties = Properties()
        file("../data.properties").inputStream().use { dataProperties.load(it) }
        buildConfigField("String", "themoviedbApiKey", "\"${dataProperties.getProperty("themoviedbApiKey") ?: ""}\"")
        buildConfigField("String", "themoviedbURL", "\"${dataProperties.getProperty("themoviedbURL") ?: ""}\"")
        buildConfigField("String", "themoviedbImageURL", "\"${dataProperties.getProperty("themoviedbImageURL") ?: ""}\"")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    //Main
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    //RoomDB
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.7.2")
    implementation("com.squareup.retrofit2:converter-gson:2.7.2")
    implementation("com.squareup.okhttp3:okhttp:4.8.1")
    //hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    //Test
    testImplementation(libs.junit)
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.0")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.0")
    androidTestImplementation("androidx.room:room-runtime:2.6.1")
    kaptAndroidTest("androidx.room:room-compiler:2.6.1")
    testImplementation("com.squareup.retrofit2:retrofit:2.7.2")
    testImplementation("com.squareup.retrofit2:converter-gson:2.7.2")
    testImplementation("com.squareup.okhttp3:logging-interceptor:4.8.1")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.10.0")
}