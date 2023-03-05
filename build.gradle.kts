import org.javamodularity.moduleplugin.extensions.TestModuleOptions

plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.8.0"
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.javamodularity.moduleplugin") version "1.8.12"
//    id("com.javiersc.semver.gradle.plugin") version "0.4.0-alpha.1"
}
val testFxVer = "4.0.16-alpha"

group = "systems.terranatal.jfxtras"
version = "0.1.2-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.jetbrains:annotations:16.0.2")

    testImplementation(kotlin("test"))

    testImplementation("org.testfx:testfx-core:$testFxVer")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.1")
    testImplementation("org.testfx:testfx-junit5:$testFxVer")

    testImplementation( "org.testfx:openjfx-monocle:jdk-12.0.1+2")
}

tasks.test {
    useJUnitPlatform()
}

java {
    version = 17
    //sourceCompatibility = org.gradle.api.JavaVersion.VERSION_17
    //targetCompatibility = org.gradle.api.JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}

javafx {
    version = "17.0.2"
    modules("javafx.controls", "javafx.graphics")
}
