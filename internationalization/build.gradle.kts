import systems.terranatal.generateMvnPublication

plugins {
    id("java")
}

group = "systems.terranatal.omnijfx"
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.yaml:snakeyaml:2.2")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
}

generateMvnPublication("internationalization", "omni-internationalization")

java {
    withJavadocJar()
    withSourcesJar()
}

tasks.test {
    useJUnitPlatform()
}
