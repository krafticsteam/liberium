subprojects {
    repositories {
        maven("https://libraries.minecraft.net")
        mavenLocal()
    }

    dependencies {
        compileOnly("com.mojang:brigadier:1.0.18")
        compileOnly("org.spigotmc:spigot:1.19-R0.1-SNAPSHOT")
        implementation(project(":api"))
    }
}