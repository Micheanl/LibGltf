import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    id("net.fabricmc.fabric-loom")
    id("org.jetbrains.kotlin.jvm")
    `maven-publish`
}

val minecraftVersion = providers.gradleProperty("minecraft_version").get()
val loaderVersion = providers.gradleProperty("loader_version").get()
val fabricApiVersion = providers.gradleProperty("fabric_api_version").get()
val fabricKotlinVersion = providers.gradleProperty("fabric_kotlin_version").get()
val lwjglVersion = providers.gradleProperty("lwjgl_version").get()
val serializationVersion = providers.gradleProperty("serialization_version").get()
val modVersion = providers.gradleProperty("mod_version").get()
val mavenGroup = providers.gradleProperty("maven_group").get()
val archivesBaseName = providers.gradleProperty("archives_base_name").get()

version = modVersion
group = mavenGroup

base {
    archivesName.set(archivesBaseName)
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    implementation("net.fabricmc:fabric-loader:$loaderVersion")
    implementation("net.fabricmc.fabric-api:fabric-api:$fabricApiVersion")
    implementation("net.fabricmc:fabric-language-kotlin:$fabricKotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
    compileOnly(files("libs/iris.jar"))

    val meshoptimizer = "org.lwjgl:lwjgl-meshoptimizer:$lwjglVersion"
    implementation(meshoptimizer)
    include(meshoptimizer)
    runtimeOnly("$meshoptimizer:natives-windows")
    runtimeOnly("$meshoptimizer:natives-windows-arm64")
    runtimeOnly("$meshoptimizer:natives-windows-x86")
    runtimeOnly("$meshoptimizer:natives-linux")
    runtimeOnly("$meshoptimizer:natives-macos")
    runtimeOnly("$meshoptimizer:natives-macos-arm64")
}

tasks.processResources {
    val version = project.version.toString()
    inputs.property("version", version)
    filesMatching("fabric.mod.json") {
        expand("version" to version)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(25)
}

tasks.withType<KotlinJvmCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_25)
    }
}

tasks.named<Jar>("jar") {
    archiveClassifier.set("fabric")
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

kotlin {
    jvmToolchain(25)
}