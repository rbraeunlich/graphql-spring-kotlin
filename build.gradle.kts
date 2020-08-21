plugins {
    java
    id("org.springframework.boot") version "2.3.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
}

group = "dev.code-n-roll"
version = "1.0-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.graphql-java-kickstart:graphql-spring-boot-starter:7.1.0")

    runtimeOnly("com.graphql-java-kickstart:graphiql-spring-boot-starter:7.1.0")

    testImplementation("junit:junit:4.13")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.graphql-java-kickstart:graphql-spring-boot-starter-test:7.1.0")
    testImplementation("org.assertj:assertj-core:3.11.1")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}


tasks.compileKotlin {
    kotlinOptions.jvmTarget = "11"
}
tasks.compileTestKotlin {
    kotlinOptions.jvmTarget = "11"
}