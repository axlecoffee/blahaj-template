plugins {
    id("dev.kikugie.stonecutter")
    id("toni.blahaj")
}

stonecutter active "1.21.1-neoforge" /* [SC] DO NOT EDIT */

tasks.register("bumpVersionAndChangelog") {
    group = "blahaj"
    doLast {
        val gradleProperties = file("gradle.properties")
        val gradlePropertiesContent = gradleProperties.readText()

        val versionRegex = Regex("""mod\.version=(\d+)\.(\d+)\.(\d+)""")
        val matchResult = versionRegex.find(gradlePropertiesContent)
        if (matchResult == null) {
            println("Error: mod.version not found in gradle.properties.")
            return@doLast
        }

        val (major, minor, patch) = matchResult.destructured

        println("Update type? (major, minor, patch):")
        val updateInput = readlnOrNull() ?: "patch"

        val newVersion = when (updateInput) {
            "major" -> "${major.toInt() + 1}.$minor.$patch"
            "minor" -> "$major.${minor.toInt() + 1}.$patch"
            "patch" -> "$major.$minor.${patch.toInt() + 1}"
            else -> "$major.$minor.${patch.toInt() + 1}"
        }

        val updatedPropertiesContent = gradlePropertiesContent.replace(
            versionRegex,
            "mod.version=$newVersion"
        )

        gradleProperties.writeText(updatedPropertiesContent)

        println("Enter the changelog for version $newVersion (separate entries with semicolons):")
        val changelogInput = readLine() ?: ""
        val changelogEntries = changelogInput.split(";").map { "- ${it.trim()}" }

        val changelogFile = file("CHANGELOG.md")
        val changelogContent = changelogFile.takeIf { it.exists() }?.readText() ?: ""

        val newChangelogContent = buildString {
            append("## $newVersion\n")
            append(changelogEntries.joinToString("\n"))
            append("\n\n")
            append(changelogContent)
        }

        changelogFile.writeText(newChangelogContent)

        println("Version bumped to $newVersion in gradle.properties.")
        println("Changelog updated with the following entries:")
        changelogEntries.forEach { println(it) }
    }
}
