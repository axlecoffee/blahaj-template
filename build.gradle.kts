plugins {
	kotlin("jvm") version "2.0.0"
	kotlin("plugin.serialization") version "2.0.0"
	id("maven-publish")
	id("application")
	id("dev.architectury.loom")// version "1.7-SNAPSHOT"
	id("me.modmuss50.mod-publish-plugin")
	id("systems.manifold.manifold-gradle-plugin")
	id("toni.blahaj")
}

blahaj {
	config {
		// yarn()
		// versionedAccessWideners()
	}
	setup {
		txnilib("1.0.22")
		forgeConfig()

		/* access Gradle's DependencyHandler
		deps.modImplementation("maven:modrinth:sodium:mc$mc-0.6.5-$loader")

		// configure Curseforge & Modrinth publish settings
		incompatibleWith("optifine")

		// add mods with Blahaj's fluent interface
		addMod("sodiumextras")
			.modrinth("sodium-extras") // override with Modrinth URL slug
			.addPlatform("1.21.1-neoforge", "neoforge-1.21.1-1.0.7")
			.addPlatform("1.21.1-fabric", "fabric-1.21.1-1.0.7") { required() } */
	}
}