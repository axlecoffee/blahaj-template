pluginManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()
		maven("https://maven.fabricmc.net/")
		maven("https://maven.architectury.dev")
		maven("https://maven.minecraftforge.net")
		maven("https://maven.neoforged.net/releases/")
		maven("https://maven.kikugie.dev/snapshots")
		maven("https://maven.kikugie.dev/releases")
		maven("https://maven.txni.dev/releases")
	}  
}

plugins {
	id("toni.blahaj") version "1.0.69"
	id("dev.kikugie.stonecutter") version "0.6-alpha.5"
    id("dev.architectury.loom") version "1.9-SNAPSHOT" apply false
	id("me.modmuss50.mod-publish-plugin") version "0.7.4" apply false
	id("systems.manifold.manifold-gradle-plugin") version "0.0.2-alpha" apply false
	kotlin("jvm") version "2.0.0" apply false
    kotlin("plugin.serialization") version "2.0.0" apply false
}

blahaj {
	init(rootProject) {
        mc("1.20.1", "fabric", "forge")
        mc("1.21.1", "fabric", "neoforge")
	}
}

rootProject.name = settings.extra["mod.name"] as String