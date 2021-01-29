<div align="center">
<img src="https://i.imgur.com/aBDylq5.png" alt="KrafticsLib">

![](https://img.shields.io/badge/Spigot-1.16.5--R0.1--SNAPSHOT-orange?style=for-the-badge)
[![](https://img.shields.io/jenkins/build?jobUrl=http%3A%2F%2Fkraftics.com%3A8080%2Fjob%2FKrafticsLib%2F&style=for-the-badge)](http://kraftics.com:8080/blue/organizations/jenkins/KrafticsLib/activity)
[![](https://img.shields.io/github/v/release/KrafticsTeam/KrafticsLib?style=for-the-badge)](https://github.com/KrafticsTeam/KrafticsLib/releases/latest)
</div>

## About

The main goal of this project is to make spigot plugin coding a lot easier.
It can help you in different ways!

## Download

You can download KrafticsLib from the [release page](https://github.com/KrafticsTeam/KrafticsLib/releases).

## Development

First, you need to add Kraftics Library to your project.
You can download it and add it as dependency using [section above](#Download).

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
    <version>0.3.0</version>
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
  compileOnly 'com.kraftics:krafticslib:0.3.0'
}
```

Read more:
  * [Creating a SQL Database](https://github.com/KrafticsTeam/KrafticsLib/wiki/Getting-Started#creating-a-sql-database)
  * [Configuration](https://github.com/KrafticsTeam/KrafticsLib/wiki/Getting-Started#configuration)

## Contributing

Do you like this project and want to contribute?<br>
You can post ideas, bug reports and pull request at the [issue tracker](https://github.com/KrafticsTeam/KrafticsLib/issues)
