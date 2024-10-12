val templateSettings = object : TxniTemplateSettings {
	// -------------------- Dependencies ---------------------- //
	override val depsHandler: TxniDependencyHandler get() = object : TxniDependencyHandler {
		override fun addGlobal(mod : ModData, deps: DependencyHandler) {

		}

		override fun addFabric(mod : ModData, deps: DependencyHandler) {

		}

		override fun addForge(mod : ModData, deps: DependencyHandler) {

		}

		override fun addNeo(mod : ModData, deps: DependencyHandler) {

		}
	}


	// ---------- Curseforge/Modrinth Configuration ----------- //
	// For configuring the dependecies that will show up on your mod page.
	override val publishHandler: TxniPublishDependencyHandler get() = object : TxniPublishDependencyHandler {
		override fun addShared(mod : ModData, deps: DependencyContainer) {
			if (mod.isFabric) {
				deps.requires("fabric-api")
			}
		}

		override fun addCurseForge(mod : ModData, deps: DependencyContainer) {

		}

		override fun addModrinth(mod : ModData, deps: DependencyContainer) {

		}
	}
}


// ---------------TxniTemplate Build Script---------------- //
//   (only edit below this if you know what you're doing)
// -------------------------------------------------------- //

plugins {
	`maven-publish`
	txnitemplate
	application
	kotlin("jvm")
	kotlin("plugin.serialization")
	id("dev.kikugie.j52j") version "1.0"
	id("dev.architectury.loom")
	id("me.modmuss50.mod-publish-plugin")
	id("systems.manifold.manifold-gradle-plugin")
}

// The manifold Gradle plugin version. Update this if you update your IntelliJ Plugin!
manifold { manifoldVersion = "2024.1.34" }

txnitemplate {
	sc = stonecutter
	settings = templateSettings
	init()
}

// Dependencies
repositories {
	exclusiveMaven("https://www.cursemaven.com", "curse.maven")
	exclusiveMaven("https://api.modrinth.com/maven", "maven.modrinth")
	exclusiveMaven("https://thedarkcolour.github.io/KotlinForForge/", "thedarkcolour")
	maven("https://maven.kikugie.dev/releases")
	maven("https://jitpack.io")
	maven("https://maven.neoforged.net/releases/")
	maven("https://maven.terraformersmc.com/releases/")
	maven("https://raw.githubusercontent.com/Fuzss/modresources/main/maven/")
	maven("https://maven.parchmentmc.org")
	maven("https://maven.su5ed.dev/releases")
}

dependencies {
	// apply the Manifold processor, do not remove this unless you want to swap back to Stonecutter preprocessor
	implementation(annotationProcessor("systems.manifold:manifold-preprocessor:${manifold.manifoldVersion.get()}")!!)
}