import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.compose)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvm("desktop")
    sourceSets {
        val desktopMain by getting  {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(projects.shared)
                implementation(compose.components.uiToolingPreview)
            }
        }
    }
}


compose.desktop {
    application {
        mainClass = "io.github.ginvavilon.slimevr.desktop.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "io.github.ginvavilon.slimevr"
            packageVersion = "1.0.0"
        }
    }
}