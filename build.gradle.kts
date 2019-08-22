import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.41"
}

version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compile("no.tornado:tornadofx:1.7.19")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}