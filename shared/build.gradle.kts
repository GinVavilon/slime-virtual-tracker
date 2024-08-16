plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvm()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }


    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.koin.android)
            implementation(libs.mlkit)
            implementation(libs.mlkit.accurate)
            implementation(libs.camerax)
            implementation(libs.camerax.camera2)
            implementation(libs.camerax.mlkit)
            implementation(libs.accompanist.permissions)
        }

        commonMain.dependencies {
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.compose.navigation)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.ktor.network)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

composeCompiler {
    enableStrongSkippingMode = true
}
compose {
    resources {
        packageOfResClass = "io.github.ginvavilon.slimevr.tracker.resources"
    }
}


android {
    namespace = "io.github.ginvavilon.slimevr.tracker"
    compileSdk = 34

    buildFeatures {
        compose = true
    }
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    dependencies{
        debugImplementation(libs.compose.ui.tooling)
    }
}

