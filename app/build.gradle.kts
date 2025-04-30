plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    /*id("com.google.gms.google-services")*/
}

android {
    namespace = "com.example.sellingserviceapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.sellingserviceapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
            buildConfigField("String", "BASE_URL", "\"http://192.168.31.190:8080/\"")
        }
        getByName("release") {
            buildConfigField("String", "BASE_URL", "\"https://api.example.com/\"")
        }

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
        buildConfig = true
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

    //implementation("androidx.core:core-splashscreen:1.1.0")

    implementation("androidx.security:security-crypto:1.1.0-alpha07")
    implementation("androidx.security:security-identity-credential:1.0.0-alpha03")

    implementation("me.onebone:toolbar-compose:2.3.5")

    implementation("androidx.datastore:datastore-preferences:1.1.3")

    implementation("androidx.compose.material3:material3:1.3.1")

    // Hilt dependencies
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("com.google.dagger:hilt-android:2.51.1")
    implementation(libs.accessibility.test.framework)
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Конвертер JSON (Gson)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    // Библиотека Gson
    implementation("com.google.code.gson:gson:2.10.1")

    //Coil
    implementation ("io.coil-kt:coil-compose:2.2.2")

    //Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

    //Navigation
    implementation("androidx.navigation:navigation-compose:2.8.7")

    //NavAnimations
    implementation ("com.google.accompanist:accompanist-navigation-animation:0.32.0")

    //ImageCropper
    implementation(libs.android.image.cropper)

    implementation(libs.androidx.lifecycle.runtime.ktx.v262) // Убедитесь, что версия актуальна
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")

    implementation ("androidx.compose.material3:material3:1.3.1")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.animation.core.lint)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}