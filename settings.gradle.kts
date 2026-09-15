// If a project asks for a Java version that isn't installed,
// use the Foojay API to find the download link for it and install it automatically.
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "gradle-test"
