# KitaBlox API — Minecraft 1.21.11 Fabric

Recreated KitaBlox API project for Minecraft 1.21.11.

## Toolchain
- Minecraft 1.21.11
- Yarn 1.21.11+build.6
- Fabric Loader 0.18.4
- Fabric API 0.141.6+1.21.11
- Fabric Loom 1.13.6
- Gradle 8.14
- Java 21

This combination is intentional so the project can sync with the Gradle 8.14 installation IntelliJ is currently using. Fabric's Loom 1.13 line supports Gradle 8.14 and Java 21.

## Build in IntelliJ
Set Gradle JVM to Java 21 and Gradle distribution to your Gradle 8.14 installation, then refresh the Gradle project.

Run the `build` task. The JAR will be in `build/libs/`.
