plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.projects.h071231006_projectfinal"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.projects.h071231006_projectfinal"
        minSdk = 24
        targetSdk = 34
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

    buildFeatures {
        viewBinding = true
    }

    packaging {
        resources.excludes += setOf(
            "META-INF/*",
            "**/*.properties"
        )
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.activity)

    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)

    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    implementation(libs.preference)

    implementation(libs.swiperefreshlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}