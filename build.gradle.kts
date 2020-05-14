plugins {
    kotlin("multiplatform") version "1.4-M1"
    kotlin("plugin.serialization") version "1.4-M1"
}
repositories {
    mavenCentral()
    maven ( url = "https://kotlin.bintray.com/kotlinx" )
    maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
}

group = "com.example"
version = "0.0.1"
val serializationRtVersion = "0.20.0-1.4-M1"

kotlin {
    jvm()
    iosX64()
    macosX64()

    targets.all {
        compilations.all {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xuse-experimental=kotlinx.serialization.UnstableDefault")
            }
        }
    }
    macosX64().binaries.getTest("DEBUG").freeCompilerArgs += arrayOf("-opt", "-Xallocator=mimalloc")
    iosX64().binaries.getTest("DEBUG").freeCompilerArgs += arrayOf("-opt", "-Xallocator=mimalloc")

    sourceSets {
        val iosX64Main by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:$serializationRtVersion")
            }
        }

        val macosX64Main by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:$serializationRtVersion")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serializationRtVersion")
            }
        }

        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serializationRtVersion")
                implementation(kotlin("stdlib-common"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("junit:junit:4.12")
                implementation("org.jetbrains.kotlin:kotlin-test")
                implementation("org.jetbrains.kotlin:kotlin-test-junit")
            }
        }
    }
}