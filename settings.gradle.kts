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
}

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