dependencies {
    api(project(":command"))
    api(project(":database"))
    api(project(":packet"))
}

tasks.processResources {
    filesMatching("plugin.yml") {
        expand("version" to version)
    }
}