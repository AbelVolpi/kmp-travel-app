import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
    }

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
//            implementation(libs.androidx.lifecycle.viewmodel.ktx)

            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.compose.viewmodel.navigation)
        }
        desktopMain.dependencies {
//            implementation(compose.desktop.currentOs)
            implementation(compose.desktop.macos_arm64)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(projects.shared)
            implementation(libs.firebase.java.sdk)
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.luacheia.kmptravelapp.backoffice.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.luacheia.kmptravelapp.backoffice"
            packageVersion = "1.0.0"
        }
    }
}
