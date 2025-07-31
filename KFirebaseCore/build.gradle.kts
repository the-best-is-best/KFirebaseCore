@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform.getCurrentOperatingSystem
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    id("maven-publish")
    id("signing")
    alias(libs.plugins.maven.publish)
}




apply(plugin = "maven-publish")
apply(plugin = "signing")


tasks.withType<PublishToMavenRepository> {
    val isMac = getCurrentOperatingSystem().isMacOsX
    onlyIf {
        isMac.also {
            if (!isMac) logger.error(
                """
                    Publishing the library requires macOS to be able to generate iOS artifacts.
                    Run the task on a mac or use the project GitHub workflows for publication and release.
                """
            )
        }
    }
}


extra["packageNameSpace"] = "io.github.kfirebase_core"
extra["groupId"] = "io.github.the-best-is-best"
extra["artifactId"] = "kfirebase-core"
extra["version"] = "2.0.0"
extra["packageName"] = "KFirebaseCore"
extra["packageUrl"] = "https://github.com/the-best-is-best/KFirebaseCore"
extra["packageDescription"] = "KFirebaseCore is a Kotlin Multiplatform library designed to streamline the integration of Firebase services in your mobile applications. With this library, developers can effortlessly initialize Firebase for both Android and iOS, enabling a unified and efficient development experience."
extra["system"] = "GITHUB"
extra["issueUrl"] = "https://github.com/the-best-is-best/KFirebaseCore/issues"
extra["connectionGit"] = "https://github.com/the-best-is-best/KFirebaseCore.git"

extra["developerName"] = "Michelle Raouf"
extra["developerNameId"] = "MichelleRaouf"
extra["developerEmail"] = "eng.michelle.raouf@gmail.com"


mavenPublishing {
    coordinates(
        extra["groupId"].toString(),
        extra["artifactId"].toString(),
        extra["version"].toString()
    )

    publishToMavenCentral(true)
    signAllPublications()

    pom {
        name.set(extra["packageName"].toString())
        description.set(extra["packageDescription"].toString())
        url.set(extra["packageUrl"].toString())
        licenses {
            license {
                name.set("Apache-2.0")
                url.set("https://opensource.org/licenses/Apache-2.0")
            }
        }
        issueManagement {
            system.set(extra["system"].toString())
            url.set(extra["issueUrl"].toString())
        }
        scm {
            connection.set(extra["connectionGit"].toString())
            url.set(extra["packageUrl"].toString())
        }
        developers {
            developer {
                id.set(extra["developerNameId"].toString())
                name.set(extra["developerName"].toString())
                email.set(extra["developerEmail"].toString())
            }
        }
    }

}


signing {
    useGpgCmd()
    sign(publishing.publications)
}

val getPackageName = extra["packageName"].toString()

kotlin {
    jvmToolchain(17)
    androidTarget {
        //https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html
        instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
    }

//    jvm()
//
//    js {
//        browser()
//        binaries.executable()
//    }
//
//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        browser()
//        binaries.executable()
//    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
        macosX64(),
        macosArm64(),
        tvosX64(),
        tvosArm64(),
        tvosSimulatorArm64(),
        watchosArm32(),
        watchosX64(),
        watchosArm64(),
        watchosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = getPackageName
            isStatic = true
        }
        it.compilations.getByName("main") {
            val firCore by cinterops.creating {
                defFile("/Users/michelleraouf/Desktop/kmm/KFirebaseCore/KFirebaseCore/nativeInterop/cinterop/FirebaseCore.def")
                packageName = "io.github.native.kfirebasecore"
            }

        }
    }


    sourceSets {
        commonMain.dependencies {
//            implementation(compose.runtime)
//            implementation(compose.foundation)
//            implementation(compose.material3)
//            implementation(compose.components.resources)
//            implementation(compose.components.uiToolingPreview)
        }

        commonTest.dependencies {
            // implementation(kotlin("test"))
//            @OptIn(ExperimentalComposeLibrary::class)
//            implementation(compose.uiTest)
        }

        androidMain.dependencies {
//            implementation(compose.uiTooling)
//            implementation(libs.androidx.activityCompose)
            api(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.common.ktx)
            implementation(libs.firebase.analytics)
        }

//        jvmMain.dependencies {
//            implementation(compose.desktop.currentOs)
//        }

//        jsMain.dependencies {
//            implementation(compose.html.core)
//        }

        iosMain.dependencies {
        }

    }
}


android {
    namespace = extra["packageNameSpace"].toString()
    compileSdk = 36

    defaultConfig {
        minSdk = 21
//        buildFeatures {
//            //enables a Compose tooling support in the AndroidStudio
//            compose = true
//        }
    }
}
