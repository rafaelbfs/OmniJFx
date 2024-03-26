import org.javamodularity.moduleplugin.extensions.TestModuleOptions
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

publishing {
    publications {
        create<MavenPublication>(publicationName) {
            from(components["java"])
            pom {
                name = "tfxtras-java"
                description = "Utility helpers to create JavaFX components using Java"
                url = "https://github.com/rafaelbfs/tfxtras/jfx"
                licenses {
                    license {
                        name = "MIT License"
                        url = "https://opensource.org/license/mit"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/rafaelbfs/tfxtras.git"
                    developerConnection = "scm:git:ssh://github.com/rafaelbfs/tfxtras.git"
                    url = "https://github.com/rafaelbfs/tfxtras/blob/main/"
                }
                developers {
                    developer {
                        id = "rafaelbfs"
                        name = "Rafael B F de Sousa"
                        email = "rafael@terranatal.co"
                        organization = "Terranatal Systems"
                    }
                }
            }

        }
    }
    signPublication(publicationName)
}

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
