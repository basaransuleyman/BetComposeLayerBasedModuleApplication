plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.ksp)
}

android.buildFeatures.buildConfig = true

android {
    namespace = "com.betapp.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 25

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            buildConfigField("String", "BASE_URL", "\"https://apivx.misli.com/api/mobile/v2/\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField("String", "BASE_URL",  "\"https://apivx.misli.com/api/mobile/v2/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    //Module Dependencies
    implementation(project(":core"))
    implementation(project(":domain"))

    //Data Layer
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson.converter)
    implementation(libs.okhttp.logging.interceptor)

    //Persistence
    implementation(libs.room)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)

    //Core
    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.ksp.compiler)

    //Test
    testImplementation(libs.mockk)
    testImplementation(libs.junit)
    implementation(libs.core.ktx)
    implementation(libs.androidx.runner)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.coroutines.test)
    testImplementation(libs.robolectric)
    testImplementation(libs.room.testing)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

}