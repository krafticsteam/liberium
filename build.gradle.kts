plugins {
    `java-library`
    `maven-publish`
    signing
}

// Constants
val project_name = "Liberium"
val project_version_suffix = if (System.getProperty("versionsuf") != null) System.getProperty("versionsuf") else ""
val project_version = "1.1.1$project_version_suffix"
val project_description = "Spigot library to make plugin coding fun and easier"
val project_url = "https://kraftics.com/liberium"
val project_jdk = "1.8"

val project_group = "com.kraftics"

val isRelease = !project_version.contains("snapshot")

allprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    group = project_group
    version = project_version
    description = project_description

//    sourceCompatibility = project_jdk
//    targetCompatibility = project_jdk

    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    dependencies {
        implementation("org.jetbrains:annotations:23.0.0")
        compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")

        testImplementation("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()

        ignoreFailures = true
    }

    // Configuring jars

//    jar {
//        from {
//            configurations.compile.collect { it.isDirectory() ? it : zipTree(it) }
//        }
//    }
//
    tasks.withType<Javadoc> {
        options.encoding = "UTF-8"
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    java {
        withSourcesJar()
        withJavadocJar()
    }
}

subprojects {
    base.archivesBaseName = "liberium-$name"

    // Maven central publishing

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])

                artifactId = base.archivesBaseName
                groupId = "com.kraftics"
                version = project_version

                pom {
                    name.set("Liberium")
                    description.set(project_description)
                    url.set("https://github.com/KrafticsTeam/Liberium")

                    organization {
                        name.set("KrafticsTeam")
                        url.set("https://kraftics.com")
                    }

                    licenses {
                        license {
                            name.set("MIT")
                            url.set("https://github.com/KrafticsTeam/Liberium/blob/master/LICENSE")
                            distribution.set("repo")
                        }
                    }

                    scm {
                        url.set("https://github.com/KrafticsTeam/Liberium")
                        connection.set("scm:git:git://github.com/KrafticsTeam/Liberium.git")
                        developerConnection.set("scm:git:ssh://git@github.com:KrafticsTeam/Liberium.git")
                    }

                    developers {
                        developer {
                            name.set("KrafticsTeam")
                        }
                    }
                }
            }
        }

        repositories {
            maven {
                if (isRelease) {
                    url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
                } else {
                    url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
                }

                val sonatypeUsername = findProperty("sonatypeUsername")?.toString()
                val sonatypePassword = findProperty("sonatypePassword")?.toString()

                if (sonatypeUsername != null && sonatypePassword != null) {
                    credentials {
                        username = sonatypeUsername
                        password = sonatypePassword
                    }
                }
            }
        }
    }

    if (isRelease) {
        signing {
            sign(publishing.publications["mavenJava"])
        }
    }
}

// Removing the parent build dir

tasks.getByName("build").doLast {
    buildDir.deleteRecursively()
}

tasks.getByName("jar").doLast {
    buildDir.deleteRecursively()
}
