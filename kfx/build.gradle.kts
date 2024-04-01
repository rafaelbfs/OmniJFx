plugins {
    kotlin("jvm") version "1.9.20"
    `maven-publish`
    id("org.openjfx.javafxplugin") version "0.1.0"
    signing
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
javafx {
    version = "21.0.2"
    modules("javafx.controls", "javafx.graphics")
}