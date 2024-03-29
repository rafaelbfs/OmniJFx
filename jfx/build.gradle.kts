import org.javamodularity.moduleplugin.extensions.TestModuleOptions
import systems.terranatal.generateMvnPublication
import systems.terranatal.signPublication

plugins {
    `java-library`
    `maven-publish`
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.javamodularity.moduleplugin") version "1.8.12"
    signing
}
val testFxVer = "4.0.16-alpha"
val publicationName = "Jfx"

group = "systems.terranatal.tfxtras"
version = rootProject.version

repositories {
    mavenCentral()
}

generateMvnPublication(publicationName, "java-fxtras")

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    testImplementation("org.testfx:testfx-core:$testFxVer")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("org.testfx:testfx-junit5:$testFxVer")
}

tasks.test {
    useJUnitPlatform()
}

javafx {
    version = "21.0.2"
    modules("javafx.controls", "javafx.graphics")
}
