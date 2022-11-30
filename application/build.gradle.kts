plugins {
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":jooq_generated"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
