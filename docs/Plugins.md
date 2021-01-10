# List of core plugins

|Plugin|Parameters|Output|Description|
|-|-|-|-|
|unix.shell|TODO|TODO|Execute Unix shell command|
|ps.shell|TODO|TODO|Execute PowerShell shell command|
|jdbc.connect|TODO|TODO|Connect to a JDBC Datasource|

## Extending and creating your own plugin

## Setup Java Maven projeto

Project must be a valid OSGi Bundle. Sample ```pom.xml```

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>io.infolayer.plugins</groupId>
    <artifactId>io.infolayer.plugins.sample</artifactId>
    <version>1.0.0</version>
    <name>Infolayer :: Plugins :: Sample project</name>
    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
    </properties>
  <packaging>bundle</packaging>
  
    <!-- Build Configuration -->
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.felix</groupId>
                <artifactId>maven-bundle-plugin</artifactId>
                <version>4.2.1</version>
                <extensions>true</extensions>
                <configuration>
                    <instructions>
                        <Bundle-SymbolicName>${project.artifactId}</Bundle-SymbolicName>
                        <Bundle-Name>${project.name}</Bundle-Name>
                        <Bundle-Description>${project.description}</Bundle-Description>
                        <Bundle-Vendor>infolayer.io</Bundle-Vendor>
                        <Bundle-Version>${project.version}</Bundle-Version>
                        <Export-Package>!*</Export-Package>
                        <Import-Package>
                            !sun.security.util,
                            *
                        </Import-Package>
                    </instructions>
                </configuration>
            </plugin>
        </plugins>
    </build>
  
    <dependencies>
        <dependency>
            <groupId>io.infolayer</groupId>
            <artifactId>io.infolayer.api</artifactId>
            <version>1.0.0</version>
        </dependency>
        <dependency>
            <groupId>io.infolayer</groupId>
            <artifactId>io.infolayer.executor</artifactId>
            <version>1.0.0</version>
        </dependency>
        <dependency>
            <groupId>org.osgi</groupId>
            <artifactId>org.osgi.framework</artifactId>
            <version>1.8.0</version>
        </dependency>
    
    </dependencies>
</project>
```
