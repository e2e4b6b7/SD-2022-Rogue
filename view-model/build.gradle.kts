plugins {
    kotlin("jvm")
    jacoco
}

dependencies {
    implementation(project(":model"))

    testImplementation(kotlin("test"))
}
