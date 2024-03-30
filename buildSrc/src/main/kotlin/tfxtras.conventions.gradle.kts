import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.`kotlin-dsl`
import org.gradle.kotlin.dsl.version

plugins {
  id("java")
  `maven-publish`
  signing
  id("io.github.gradle-nexus.publish-plugin")
}

repositories {
  mavenCentral()
}

if (providers.gradleProperty("ossrh.username").isPresent &&
  providers.gradleProperty("ossrh.password").isPresent
) {
    nexusPublishing {
    repositories {
      sonatype {  //only for users registered in Sonatype after 24 Feb 2021
        nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
        snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        username.set(providers.gradleProperty("ossrh.username").get())
        password.set(providers.gradleProperty("ossrh.password").get())
      }
    }
  }
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
  withJavadocJar()
  withSourcesJar()
}
