plugins {
    kotlin("jvm")
    jacoco
}

dependencies {
    implementation(project(":model"))

    implementation("io.insert-koin", "koin-core", "3.1.6")

    testImplementation(kotlin("test"))
}
