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
	id("dev.kikugie.stonecutter") version "0.6-alpha.5"
	id("toni.blahaj") version "1.0.55"
    id("dev.kikugie.j52j") version "1.0" apply false
    id("dev.architectury.loom") version "1.9-SNAPSHOT" apply false
    id("me.modmuss50.mod-publish-plugin") version "0.7.4" apply false
    id("systems.manifold.manifold-gradle-plugin") version "0.0.2-alpha" apply false
}

//blahaj {
//	init(rootProject)
//}

stonecutter {
	kotlinController = true
	centralScript = "build.gradle.kts"

	create(rootProject) {
		fun mc(version: String, vararg loaders: String) {
			for (it in loaders)
			{
				val versStr = "$version-$it";
				mkdir("versions/$versStr")
				vers(versStr, version)
			}
		}

		mc("1.20.1", "fabric" , "forge")
		mc("1.21.1", "fabric" , "neoforge")
	}
}

rootProject.name = "TxniTemplate"