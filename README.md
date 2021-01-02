![e](https://raw.githubusercontent.com/KrafticsTeam/KrafticsLib/master/images/banner.png)
# KrafticsLib
KrafticsLib is a library for plugins created by Kraftics.
But that doesn't mean you can't use it!<br>
This library so far contains easier database and config management.

## Getting Started
First of all, you need to add Kraftics Library to your project.
You can download jar from [github releases](https://github.com/KrafticsTeam/KrafticsLib/releases).<br>
Or you can use one of these build tools:

### Maven
```xml
<repositories>
  <repository>
    <id>kraftics</id>
    <url>http://kraftics.com:8081/repository/maven-releases/</url>
  </repository>
</repositories>
<dependencies>
  <dependency>
    <groupId>com.kraftics</groupId>
    <artifactId>krafticslib</artifactId>
    <version>0.2.0-beta</version>
    <scope>provided</scope>
  </dependency>
</dependencies>
```

### Gradle
```gradle
repositories {
  maven { url = 'http://kraftics.com:8081/repository/maven-releases/' }
}

dependencies {
  compileOnly 'com.kraftics:krafticslib:0.2.0-beta'
}
```
Now you have KrafticsLib in your plugin.<br>
You can read more about:
  * [Creating a SQL Database](https://github.com/KrafticsTeam/KrafticsLib/wiki/Getting-Started#creating-a-sql-database)
  * [Configuration](https://github.com/KrafticsTeam/KrafticsLib/wiki/Getting-Started#configuration)
