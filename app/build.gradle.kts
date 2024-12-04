plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

}

android {
    namespace = "com.example.projectcapstone"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.projectcapstone"
        minSdk = 25
        targetSdk = 34
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation ("androidx.compose.ui:ui:1.5.3")
    implementation ("androidx.compose.material:material:1.5.3")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.5.3")
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.espresso.core)
    implementation(libs.androidx.runtime.livedata)
    debugImplementation ("androidx.compose.ui:ui-tooling:1.5.3")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-livedata:2.8.3")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation ("androidx.room:room-runtime:2.5.2")
    implementation ("androidx.room:room-ktx:2.5.2")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("androidx.compose.material3:material3:1.2.1")
    implementation ("androidx.compose.material:material-icons-extended:1.7.5")
    implementation ("com.michael-bull.kotlin-result:kotlin-result:1.1.16")
    implementation ("com.google.dagger:hilt-android:2.48")
    debugImplementation ("com.github.chuckerteam.chucker:library:4.0.0")
    releaseImplementation ("com.github.chuckerteam.chucker:library-no-op:4.0.0")
    implementation ("com.squareup.okhttp3:okhttp:4.11.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation ("androidx.compose.material3:material3:1.2.0")




}