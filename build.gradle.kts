import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion = "1.5.4"
val githubProject = "bluefireoly/kotlin-smtp"

plugins {
    kotlin("jvm") version "1.5.0"
    `java-library`
    `maven-publish`
    signing
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

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    repositories {
        maven("https://oss.sonatype.org/service/local/staging/deploy/maven2") {
            name = "ossrh"
            credentials(PasswordCredentials::class)
        }
    }

    publications {
        create<MavenPublication>(project.name) {
            from(components["java"])

            this.groupId = project.group.toString()
            this.artifactId = project.name
            this.version = project.version.toString()

            pom {
                name.set(project.name)
                description.set(project.description)

                developers {
                    developer {
                        name.set("bluefireoly")
                    }
                }

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                url.set("https://github.com/$githubProject")

                scm {
                    connection.set("scm:git:git://github.com/$githubProject.git")
                    url.set("https://github.com/$githubProject/tree/main")
                }
            }
        }
    }
}
