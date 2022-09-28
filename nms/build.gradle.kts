subprojects {
    repositories {
        maven("https://repo.codemc.io/repository/nms/")
    }

    dependencies {
        compileOnly("org.spigotmc:spigot:1.19-R0.1-SNAPSHOT")
        implementation(project(":api"))
    }
}