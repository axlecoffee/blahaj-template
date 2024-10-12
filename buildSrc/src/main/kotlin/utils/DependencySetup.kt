package utils

import TxniTemplateBuild
import gradle.kotlin.dsl.accessors._523dc74e2e9552463686721a7434f18b.*
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.getByType
import systems.manifold.ManifoldExtension

fun dependencies(template : TxniTemplateBuild): (DependencyHandlerScope).() -> Unit = { template.apply {
    minecraft("com.mojang:minecraft:${mod.mcVersion}")

    // apply the Manifold processor, do not remove this unless you want to swap back to Stonecutter preprocessor
    implementation(annotationProcessor("systems.manifold:manifold-preprocessor:${project.extensions.getByType<ManifoldExtension>().manifoldVersion.get()}")!!)

    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    @Suppress("UnstableApiUsage")
    mappings(project.loom.layered {
        officialMojangMappings()
        val parchmentVersion = when (mod.mcVersion) {
            "1.18.2" -> "1.18.2:2022.11.06"
            "1.19.2" -> "1.19.2:2022.11.27"
            "1.20.1" -> "1.20.1:2023.09.03"
            "1.21.1" -> "1.21:2024.07.28"
            else -> ""
        }
        parchment("org.parchmentmc.data:parchment-$parchmentVersion@zip")
    })

    settings.depsHandler.addGlobal(mod, dependencies)

    if (mod.isFabric) {
        modImplementation(settings.depsHandler.modrinth("modmenu", property("deps.modmenu")))

        settings.depsHandler.addFabric(mod, dependencies)
        modImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fapi")}")
        modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")

        if (setting("runtime.sodium"))
            modRuntimeOnly(settings.depsHandler.modrinth("sodium", when (mod.mcVersion) {
                "1.21.1" -> "mc1.21-0.6.0-beta.1-fabric"
                "1.20.1" -> "mc1.20.1-0.5.11"
                else -> null
            }))

        // JarJar Forge Config API
        if (setting("options.forgeconfig"))
            include(
                when (mod.mcVersion) {
                    "1.19.2" -> modApi("net.minecraftforge:forgeconfigapiport-fabric:${property("deps.forgeconfigapi")}")
                    else -> modApi("fuzs.forgeconfigapiport:forgeconfigapiport-fabric:${property("deps.forgeconfigapi")}")
                }!!
            )
    }

    if (mod.isForge) {
        settings.depsHandler.addForge(mod, dependencies)
        "forge"("net.minecraftforge:forge:${mod.mcVersion}-${property("deps.fml")}")
    }

    if (mod.isNeo) {
        settings.depsHandler.addNeo(mod, dependencies)
        "neoForge"("net.neoforged:neoforge:${property("deps.fml")}")

        project.loom.neoForge {

        }
        if (setting("runtime.sodium"))
            runtimeOnly(settings.depsHandler.modrinth("sodium", "mc1.21-0.6.0-beta.1-neoforge"))
    }

    vineflowerDecompilerClasspath("org.vineflower:vineflower:1.10.1")

}}