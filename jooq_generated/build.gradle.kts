plugins {
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
