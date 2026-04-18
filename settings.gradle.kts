pluginManagement {
	repositories {
		mavenLocal()
		mavenCentral()
		gradlePluginPortal()
		maven("https://maven.fabricmc.net/")
		maven("https://maven.architectury.dev")
		maven("https://maven.minecraftforge.net")
		maven("https://maven.neoforged.net/releases/")
		maven("https://maven.kikugie.dev/snapshots")
		maven("https://maven.kikugie.dev/releases")
		maven("https://maven.axle.coffee/releases")
	}  
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
	id("dev.architectury.loom") version "1.14-SNAPSHOT" apply false
	id("dev.architectury.loom-no-remap") version "1.14-SNAPSHOT" apply false
	kotlin("jvm") version "2.1.21" apply false
	kotlin("plugin.serialization") version "2.1.21" apply false
	id("toni.blahaj") version "3.0.0"
	id("dev.kikugie.stonecutter") version "0.9.1"
}

blahaj {
	init(rootProject) {
		mc("1.20.1", "fabric", "forge")
		mc("1.21.1", "fabric", "neoforge")
		mc("1.21.4", "fabric", "neoforge")
		mc("26.1.2", "fabric", "neoforge")
	}
}

rootProject.name = settings.extra["mod.name"] as String
