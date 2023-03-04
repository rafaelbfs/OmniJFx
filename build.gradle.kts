import org.javamodularity.moduleplugin.extensions.TestModuleOptions

plugins {
    kotlin("jvm") version "1.8.0"
    application
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.javamodularity.moduleplugin") version "1.8.12"
}
val testFxVer = "4.0.16-alpha"

group = "systems.terranatal.jfxtras"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.jetbrains:annotations:16.0.2")

    testImplementation(kotlin("test"))

    testImplementation("org.testfx:testfx-core:$testFxVer")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.1")
    testImplementation("org.testfx:testfx-junit5:$testFxVer")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

javafx {
    version = "17.0.2"
    modules("javafx.controls", "javafx.graphics")
}
