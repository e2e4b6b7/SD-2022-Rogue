plugins {
    kotlin("jvm")
    jacoco
}

dependencies {
    implementation(project(":view-model"))

    testImplementation(kotlin("test"))
}
