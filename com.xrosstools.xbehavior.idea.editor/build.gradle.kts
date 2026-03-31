plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.10.2"
}

group = "com.xrosstools"
version = "1.2.0"

val sandbox  : String by project

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
    intellijPlatform {
        intellijIdeaUltimate("2025.3.3")
        bundledPlugin("com.intellij.java")
    }

    compileOnly(files("$sandbox/com.xrosstools.idea.gef.zip"))
    implementation(files("libs/dom4j-1.6.1.jar"))
}

intellijPlatform {
    instrumentCode = true
    buildSearchableOptions = false

    pluginConfiguration {
        //<idea-version since-build="193.6911.18"/>
        ideaVersion {
            sinceBuild = "183.6156.11"
        }

        changeNotes = """
            <em>1.2.0</em>Support code generation for action and condition node. Support node label.<br>
            <em>1.1.1</em>Fix model factory code generation bug.<br>
            <em>1.1.0</em>Optimize model factory code generation.<br>
            <em>1.0.0</em>Support basic concept of behavior tree.<br>
        """.trimIndent()
    }

    pluginVerification {
        ides {
            // 验证最老和最新的目标版本
            ide("IC-2018.3.6")
            ide("IC-2020.3.4")
            ide("IC-2025.3.3")
        }
    }
}

intellijPlatformTesting {
    runIde {
        register("runWithLocalPlugins") {
            plugins {
                val pluginFiles = file(sandbox).listFiles()
                pluginFiles.forEach { file ->
                    if (!file.name.contains(project.name)) {
                        localPlugin(file.absolutePath)
                    }
                }
            }
        }
    }
}

tasks.named("runIde") {
    dependsOn("runWithLocalPlugins")
}

tasks {
    buildPlugin {
        archiveFileName.set("${project.name}.zip")
    }
}
