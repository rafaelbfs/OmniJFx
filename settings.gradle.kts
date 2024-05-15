plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "omnijfx"
include("jfx")
include("kfx")
include("internationalization")
