plugins {
    java
    kotlin("jvm") version "1.3.72"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    testCompile("junit:junit:4.12")
}

compileKotlin {
    kotlinOptions.jvmTarget = "11"
}
compileTestKotlin {
    kotlinOptions.jvmTarget = "11"
}