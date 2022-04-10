plugins {
    kotlin("jvm")
    jacoco
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx", "kotlinx-serialization-json", "1.3.2")
}
