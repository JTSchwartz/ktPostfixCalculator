import no.tornado.fxlauncher.gradle.FXLauncherExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

version = "1.0"

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("no.tornado:fxlauncher-gradle-plugin:1.0.20")
    }
}

plugins {
    kotlin("jvm") version "1.3.41"
}

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

apply {
    plugin("no.tornado.fxlauncher")
}

configure<FXLauncherExtension> {
    applicationVendor = "Jacob Schwartz (jacob@jtschwartz.com)"
    // Base URL where you will host the application artifacts
    applicationUrl = "https://dist.jtschwartz.dev/ktPostfixCalculator"
    deployTarget = "root@3.208.210.5:/var/www/dist/ktPostfixCalculator"
    applicationMainClass = "CalcApp"
    acceptDowngrade = false
}