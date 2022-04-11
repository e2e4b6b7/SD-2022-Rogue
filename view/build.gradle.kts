plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.6.10"
    jacoco
}

dependencies {
    implementation(project(":view-model"))
    implementation(project(":model"))

    implementation("org.jetbrains.kotlinx", "kotlinx-serialization-json", "1.3.2")
    implementation("io.insert-koin", "koin-core", "3.1.6")

    testImplementation(kotlin("test"))
}

tasks.register("fatJar", type = Jar::class) {
    archiveBaseName.set("rogue")
    manifest {
        attributes["Main-Class"] = "sd.rogue.view.MainKt"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks["jar"] as CopySpec)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    dependsOn("build")
}

