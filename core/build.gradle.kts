dependencies {
    api(project(":command"))
    api(project(":database"))
    api(project(":packet"))
}

tasks.withType<Javadoc> {
    dependsOn(project(":command").tasks.withType(Javadoc::class))
    dependsOn(project(":database").tasks.withType(Javadoc::class))
    dependsOn(project(":packet").tasks.withType(Javadoc::class))
}

tasks["jar"].mustRunAfter(project(":packet").tasks["jar"], project(":command").tasks["jar"], project(":database").tasks["jar"])