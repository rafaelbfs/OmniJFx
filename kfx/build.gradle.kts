import systems.terranatal.generateMvnPublication

/*
 * Copyright © 2024, Rafael Barros Felix de Sousa @ Terranatal Systems
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice,
 *       this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of {{ project }} nor the names of its contributors
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

plugins {
    kotlin("jvm") version "1.9.20"
    id("org.jetbrains.dokka") version "1.9.20"
    `maven-publish`
    id("org.openjfx.javafxplugin") version "0.1.0"
    signing
}

group = "systems.terranatal.omnijfx"
version = rootProject.version
val testFxVer = "4.0.17"

repositories {
    mavenCentral()
}

val dokkaHtml by tasks.getting(org.jetbrains.dokka.gradle.DokkaTask::class)

val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    dependsOn(dokkaHtml)
    archiveClassifier.set("javadoc")
    from(dokkaHtml.outputDirectory)
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

generateMvnPublication("Kfx", "kotlin-fxtras",
    "Kotlin helpers and extensions to help developing JavaFX apps.",
    components["kotlin"],
    javadocJar, sourcesJar)

dependencies {
    implementation(project(":internationalization"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.8.1")


    testImplementation("org.testfx:testfx-core:$testFxVer")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("org.testfx:testfx-junit5:$testFxVer")
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