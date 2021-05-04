val ktorVersion = "1.5.4"

plugins {
    kotlin("jvm") version "1.4.32"
}

group = "net.axay"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("io.ktor:ktor-network:$ktorVersion")
}
