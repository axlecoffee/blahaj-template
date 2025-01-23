---
outline: deep
---

# Introduction to Multiversion Modding

*Hello, and welcome to multiversion hell!*

## What's in Blahaj?




The [default template](https://github.com/txnimc/TxniTemplate) is set up with the following:

- Fabric, Forge, and NeoForge in one sourceset, using Stonecutter + Manifold preprocessor
- Preconfigured support for all modern 'LTS' versions, in one sourceset (1.20.1, 1.21 by default, other versions can be added)
- Mojang Mappings with Parchment
- Mixins for all platforms
- Automatic dependency injection into `fabric.mods.json`, `mods.toml`, and Curseforge/Modrinth release dependencies.
- Access Wideners (automatically converted to Forge Access Transformers)
- GitHub Actions workflows to automatically deploy published jars

## Helper Tasks

Additionally, Blahaj provides some helper tasks for automating common mod dev things:

- `renameExampleMod`, which you can run once after forking the template and editing gradle.properties to instantly get started.
- `bumpVersionAndChangelog`, which will ask you for a version type (major, minor, patch) and changelog, bump the gradle.properties, and automatically append CHANGELOG.md
- `copyToModrinthLauncher`, which will run a chiseled buildAll and copy the files to designated Modrinth App profiles for testing ([more on that here](/modrinthdebugging))
- `publishAllRelease`, a standard mod-publish task, which will automatically publish all versions to Modrinth and Curseforge.
- `publishAllMaven`, which does the same automated publishing of all versions, but for your own Maven. I recommend [Reposilite.](https://reposilite.com/)

## Optional Features

Blahaj provides full opt-in support for the following, each activated with one line in Gradle setup:

- TxniLib, which provides runtime multiversion abstractions
- Cross-platform Forge Config (FC API Port)
- Yarn mappings (smh)
- Version specific or platform specific Access Wideners
- Version specific or platform specific Mixin configs
- Sodium and Iris in your dev instance
- EMI in your dev instance

## Why Multiversion?

This template combines two different things---*multiversion* and *multiloader*.

Traditionally, if you want to support multiple Minecraft versions, you would need to create multiple Git branches for each version.
This can be a major pain if you are actively maintaining each one, so instead, *multiversion* Minecraft mods 
build all jars from a single branch, often called a *monorepo*.

Of course, you still need some way to separate version specific code. This is typically done with some kind of *preprocessor*,
which enables conditional compilation so that each Minecraft version target only includes the code that works for it.
While this makes Gradle scripts more complicated to set up and understand, it massively simplifies updates across many supported versions.
Fortunately, setting up Gradle is largely a one time operation, and this template takes care of most of it.

Likewise, if you wanted to support both Forge and Fabric, you would also need separated Git branches, and this can compound 
the maintenance problem when trying to support multiple versions as well. To make this easier, **multiloader** Minecraft mods build from one repo,
typically with a lot of shared code (since both modloaders are just Minecraft!). 

You've probably heard of Architectury, which is a multiloader Gradle setup that uses separated `common`, `forge`, and `fabric`
sourcesets. However, it's just simply cleaner to combine both, since they can both be set up with preprocessors to build from a single `src` directory.

Note that you don't actually need to do both multiloader and multiversion---you can remove one or the other and this setup will work the same.