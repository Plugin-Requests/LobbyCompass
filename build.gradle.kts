plugins {
    id("java")
}

group = "net.savagedev"
version = "1.0.0-SNAPSHOT"

java {
    targetCompatibility = JavaVersion.VERSION_17
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()

    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
}

tasks {
    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        from(sourceSets.main.get().resources.srcDirs) {
            expand(Pair("version", project.version))
                .include("plugin.yml")
        }
    }
}