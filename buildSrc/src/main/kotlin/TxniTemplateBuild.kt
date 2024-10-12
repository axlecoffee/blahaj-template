import dev.kikugie.stonecutter.StonecutterBuild
import gradle.kotlin.dsl.accessors._523dc74e2e9552463686721a7434f18b.*
import gradle.kotlin.dsl.accessors._523dc74e2e9552463686721a7434f18b.base
import gradle.kotlin.dsl.accessors._523dc74e2e9552463686721a7434f18b.include
import gradle.kotlin.dsl.accessors._523dc74e2e9552463686721a7434f18b.loom
import gradle.kotlin.dsl.accessors._523dc74e2e9552463686721a7434f18b.minecraft
import gradle.kotlin.dsl.accessors._523dc74e2e9552463686721a7434f18b.modApi
import gradle.kotlin.dsl.accessors._523dc74e2e9552463686721a7434f18b.remapJar
import net.fabricmc.loom.task.RemapJarTask
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.bundling.Tar
import org.gradle.api.tasks.bundling.Zip
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.language.jvm.tasks.ProcessResources
import java.io.File
import me.modmuss50.mpp.ModPublishExtension;
import net.fabricmc.loom.api.LoomGradleExtensionAPI
import org.gradle.api.JavaVersion
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.*
import systems.manifold.ManifoldExtension

@Suppress("MemberVisibilityCanBePrivate", "unused")
open class TxniTemplateBuild internal constructor(val project: Project)  {
    lateinit var loader : String
    lateinit var sc : StonecutterBuild
    lateinit var settings : TxniTemplateSettings
    lateinit var mod : ModData

    lateinit var modrinthPath: String
    lateinit var modrinthDir: File

    fun setting(prop : String) : Boolean = project.properties[prop] == "true"
    fun property(prop : String) : Any? = project.properties[prop]

    fun init() {
        loader = project.loom.platform.get().name.lowercase()
        mod = ModData.from(this)

        modrinthDir = File(project.properties["client.modrinth_profiles_dir"].toString())
        modrinthPath = when (loader) {
            "fabric" -> "Fabric ${mod.mcVersion}"
            "neoforge" -> "NeoForge ${mod.mcVersion}"
            "forge" -> "Forge ${mod.mcVersion}"
            else -> ""
        }

        // Versioning Setup
        project.run {
            version = "${mod.version}-${mod.mcVersion}"
            group = mod.group

            base { archivesName.set("${mod.id}-${mod.loader}") }
        }

        // The manifold Gradle plugin version. Update this if you update your IntelliJ Plugin!
        project.extensions.getByType<ManifoldExtension>().apply { manifoldVersion = "2024.1.34" }

        // Loom config
        project.loom(utils.loomSetup(this))

        // Dependencies
        DependencyHandlerScope.of(project.dependencies).apply(utils.dependencies(this))

        // Tasks
        project.tasks.apply(utils.tasks(this))

        project.sourceSets {
            main {
                resources {
                    srcDir("src/main/generated")
                    exclude(".cache/")
                }
            }
        }

        sc.apply {
            val j21 = eval(mod.mcVersion, ">=1.20.6")
            project.java {
                withSourcesJar()
                sourceCompatibility = if (j21) JavaVersion.VERSION_21 else JavaVersion.VERSION_17
                targetCompatibility = if (j21) JavaVersion.VERSION_21 else JavaVersion.VERSION_17
            }
        }

        // this won't let me move it to a different class so fuck it, it goes here
        project.extensions.getByType<ModPublishExtension>().apply(fun ModPublishExtension.() {
            file = project.tasks.remapJar.get().archiveFile
            additionalFiles.from(project.tasks.remapSourcesJar.get().archiveFile)
            displayName =
                "${mod.name} ${mod.loader.replaceFirstChar { it.uppercase() }} ${mod.version} for ${property("mod.mc_title")}"
            version = mod.version
            changelog = project.rootProject.file("CHANGELOG.md").readText()
            type = STABLE
            modLoaders.add(mod.loader)

            val targets = property("mod.mc_targets").toString().split(' ')

            dryRun = project.providers.environmentVariable("MODRINTH_TOKEN").getOrNull() == null ||
                    project.providers.environmentVariable("CURSEFORGE_TOKEN").getOrNull() == null

            modrinth {
                projectId = property("publish.modrinth").toString()
                accessToken = project.providers.environmentVariable("MODRINTH_TOKEN")
                targets.forEach(minecraftVersions::add)
                val deps = DependencyContainer(null, this)
                settings.publishHandler.addModrinth(mod, deps)
                settings.publishHandler.addShared(mod, deps)
            }

            curseforge {
                projectId = property("publish.curseforge").toString()
                accessToken = project.providers.environmentVariable("CURSEFORGE_TOKEN")
                targets.forEach(minecraftVersions::add)
                val deps = DependencyContainer(this, null)
                settings.publishHandler.addCurseForge(mod, deps)
                settings.publishHandler.addShared(mod, deps)
            }
        })

        project.extensions.getByType<PublishingExtension>().apply(utils.mavenPublish(this))
    }


}
