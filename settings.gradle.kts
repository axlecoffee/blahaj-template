pluginManagement {
	repositories {
		maven("https://maven.axle.coffee/releases")
		mavenCentral()
		gradlePluginPortal()
		maven("https://maven.fabricmc.net/")
		maven("https://maven.architectury.dev")
		maven("https://maven.minecraftforge.net")
		maven("https://maven.kikugie.dev/snapshots")
		maven("https://maven.kikugie.dev/releases")
	}
}

plugins {
	id("dev.architectury.loom") version "1.10-SNAPSHOT" apply false
	kotlin("jvm") version "2.3.0" apply false
	kotlin("plugin.serialization") version "2.3.0" apply false
	id("coffee.axle.blahaj") version "3.0.0"
	id("dev.kikugie.stonecutter") version "0.6-alpha.5"
}

blahaj {
	init(rootProject) {
		mc("1.21.10", "fabric")
		mc("1.21.11", "fabric")
		mc("26.1.2", "fabric")
	}
}

rootProject.name = settings.extra["mod.name"] as String
