plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.a24_08_avl"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.a24_08_avl"
        minSdk = 29
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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

    flavorDimensions += "platform"

   productFlavors {

       // Platform dimension
       create("mobile") {
           dimension = "platform"
           // Configuration spécifique pour mobile
       }
       create("automotive") {
           dimension = "platform"
           // Configuration spécifique pour tablette
       }

   }
}

dependencies {

    //Android for car
    implementation ("androidx.car.app:app:1.4.+")
    implementation ("androidx.car.app:app-automotive:1.4.+")

    //Permission
    implementation ("com.google.accompanist:accompanist-permissions:+")

    //Requete web
    implementation("com.squareup.okhttp3:okhttp:+")
    //Parsing JSON
    implementation("com.google.code.gson:gson:+")

    //Pour les image url
    implementation("com.github.bumptech.glide:compose:1.0.0-beta01")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(files("src\\main\\libs\\android.car.jar"))
    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}