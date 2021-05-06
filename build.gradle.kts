import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion = "1.5.4"

plugins {
    kotlin("jvm") version "1.5.0"
}

group = "net.axay"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("io.ktor:ktor-network:$ktorVersion")

    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
}

tasks {
    withType<JavaCompile> {
        options.release.set(11)
    }
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    test {
        useJUnitPlatform()
    }
}
