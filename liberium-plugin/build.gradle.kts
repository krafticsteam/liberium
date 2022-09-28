dependencies {
    api(project(":liberium-api"))
    api(project(":liberium-database"))
//    api(project(":packet"))
}

tasks.processResources {
    filesMatching("plugin.yml") {
        expand("version" to version)
    }
}