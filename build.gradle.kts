import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.fabric.loom)
    alias(libs.plugins.ktlint)
}

group = "io.github.hiiragi283"
version = "0.0.0+1.16.5"

repositories {
    mavenCentral()
    maven(url = "https://cursemaven.com") {
        content { includeGroup("curse.maven") }
    }
    maven(url = "https://api.modrinth.com/maven") {
        content { includeGroup("maven.modrinth") }
    }
    // AE2
    maven(url = "https://modmaven.dev/") {
        content { includeGroup("appeng") }
    }
    maven(url = "https://mod-buildcraft.com/maven") {
        content { includeGroup("alexiil.mc.lib") }
    }
    maven(url = "https://raw.githubusercontent.com/Technici4n/Technici4n-maven/master/") {
        content {
            includeGroup("dev.technici4n")
            includeGroup("net.fabricmc.fabric-api")
        }
    }
    maven(url = "https://dvs1.progwml6.com/files/maven") // JEI
    maven(url = "https://maven.architectury.dev")
    maven(url = "https://maven.shedaniel.me") // REI
    maven(url = "https://maven.terraformersmc.com/releases")
    maven(url = "https://thedarkcolour.github.io/KotlinForForge") // KfF
}

dependencies {
    minecraft(libs.minecraft)
    mappings("net.fabricmc:yarn:${libs.versions.fabric.yarn.get()}:v2")
    modImplementation(libs.bundles.mods.fabric) {
        exclude(module = "fabric-api")
        exclude(module = "fabric-loader")
    }
    modCompileOnly(libs.bundles.mods.compile) {
        exclude(module = "fabric-api")
        exclude(module = "fabric-loader")
    }
    modLocalRuntime(libs.bundles.mods.debug) {
        exclude(module = "fabric-api")
        exclude(module = "fabric-loader")
        exclude(module = "architectury")
    }
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

loom {
    // accessWidenerPath = file("src/main/resources/ht_materials.accesswidener")
    runs {
        getByName("client") {
            programArg("--username=Developer")
            vmArgs("-Dmixin.debug.export=true", "-Dorg.apache.logging.log4j.level=DEBUG")
        }
        getByName("server") {
            runDir = "server"
            vmArgs("-Dmixin.debug.export=true", "-Dorg.apache.logging.log4j.level=DEBUG")
        }
    }
}

kotlin {
    jvmToolchain(8)
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
        freeCompilerArgs.add("-Xjvm-default=all")
    }
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

ktlint {
    reporters {
        reporter(ReporterType.HTML)
        reporter(ReporterType.SARIF)
    }
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}

tasks {
    test {
        useJUnitPlatform()
    }

    compileJava {
        options.encoding = "UTF-8"
    }

    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }
    }
    jar {
        from("LICENSE") {
            rename { "${it}_${project.base.archivesName.get()}" }
        }
    }
}
