<div align="center">
<img src="https://i.imgur.com/aBDylq5.png" alt="KrafticsLib">

![](https://img.shields.io/badge/Spigot-1.16.4--R0.1--SNAPSHOT-orange?style=for-the-badge)
[![](https://img.shields.io/jenkins/build?jobUrl=http%3A%2F%2Fkraftics.com%3A8080%2Fjob%2FKrafticsLib%2F&style=for-the-badge)](http://kraftics.com:8080/job/KrafticsLib/)
[![](https://img.shields.io/github/v/release/KrafticsTeam/KrafticsLib?style=for-the-badge)](https://github.com/KrafticsTeam/KrafticsLib/releases/latest)
</div>

## About

KrafticsLib is a library originally made for plugins created by Kraftics.

The main goal of this project is to make plugin coding a lot easier.
For now, it only contains easier database and config management.
But that will change in the future.

## Installation

1. Download the latest build from the [releases page](https://github.com/KrafticsTeam/KrafticsLib/releases)
2. Move the jar that you downloaded into plugins folder in your server
3. Run the server

## Development

First of all, you need to add Kraftics Library to your project.
You can download jar from the [releases page](https://github.com/KrafticsTeam/KrafticsLib/releases)

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
    <version>0.2.0</version>
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
  compileOnly 'com.kraftics:krafticslib:0.2.0'
}
```
Now you have KrafticsLib in your plugin.

Read more:
  * [Creating a SQL Database](https://github.com/KrafticsTeam/KrafticsLib/wiki/Getting-Started#creating-a-sql-database)
  * [Configuration](https://github.com/KrafticsTeam/KrafticsLib/wiki/Getting-Started#configuration)

## Contributing

Do you like this project and want to contribute?<br>
You can post ideas, bug reports and pull request at the [issue tracker](https://github.com/KrafticsTeam/KrafticsLib/issues)
