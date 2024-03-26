plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.9.20"
}

group = "systems.terranatal.tfxtras"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

dependencies {
    implementation("io.github.gradle-nexus:publish-plugin:2.0.0-rc-2")
}
