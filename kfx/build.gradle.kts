plugins {
    kotlin("jvm") version "1.9.20"
}

group = "systems.terranatal.tfxtras"
version = "0.2.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}