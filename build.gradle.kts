plugins {
    `java-library`
    `maven-publish`
    signing

    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

version = "$version+v2"

// Include build number in version
val build = System.getenv("BUILD")?.toString()
if (build != null) {
    version = "$version.$build"
}

allprojects {
    apply(plugin = "java-library")
    apply(plugin = "com.github.johnrengelman.shadow")

    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://libraries.minecraft.net")
    }

    dependencies {
        implementation("org.jetbrains:annotations:23.0.0")
        compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")

        testImplementation("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    tasks.test {
        useJUnitPlatform()

        ignoreFailures = true
    }

    tasks.javadoc {
        options.encoding = "UTF-8"
    }

    tasks.compileJava {
        options.encoding = "UTF-8"
    }

    // Add shadowJar to assemble task
    tasks.assemble {
        dependsOn("shadowJar")
    }

    java {
        withSourcesJar()
        withJavadocJar()
    }
}

subprojects {
    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    val baseName = "${rootProject.name}-$name"

    tasks.withType<Jar> {
        archiveBaseName.set(baseName)
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components["java"])

                artifactId = baseName
                groupId = project.group.toString()
                version = project.version.toString()

                pom {
                    description.set(project.description)
                    url.set("https://github.com/krafticsteam/liberium")

                    organization {
                        name.set("KrafticsTeam")
                        url.set("https://kraftics.com")
                    }

                    licenses {
                        license {
                            name.set("MIT")
                            url.set("https://github.com/krafticsteam/liberium/blob/master/LICENSE")
                            distribution.set("repo")
                        }
                    }

                    scm {
                        url.set("https://github.com/krafticsteam/liberium")
                        connection.set("scm:git:git://github.com/krafticsteam/liberium.git")
                        developerConnection.set("scm:git:ssh://git@github.com:krafticsteam/liberium.git")
                    }

                    developers {
                        developer {
                            url.set("https://panda885.github.io")
                            name.set("Panda885")
                        }
                    }
                }
            }
        }
    }

    if (!version.toString().endsWith("-SNAPSHOT")) {
        signing {
            useInMemoryPgpKeys(System.getenv("SONATYPE_PGP_KEY"), System.getenv("SONATYPE_PGP_PASSWORD"))

            sign(publishing.publications["maven"])
        }
    }
}

nexusPublishing {
    repositories {
        sonatype {
            username.set(System.getenv("SONATYPE_USERNAME"))
            password.set(System.getenv("SONATYPE_PASSWORD"))
        }
    }
}