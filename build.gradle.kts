@file:Suppress("GradlePackageUpdate", "VulnerableLibrariesLocal")

plugins {
    java
    `kotlin-dsl`
    application
    id("nu.studer.jooq") version "8.0" apply false
}

repositories {
    mavenCentral()
}

allprojects {

    group = "ru.aleksseii"
    version = "1.0-SNAPSHOT"

    apply {
        plugin("java")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("nu.studer.jooq")
    }


    dependencies {

        testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.1")

        testImplementation("org.hamcrest:hamcrest-all:1.3")

        testImplementation("org.mockito:mockito-inline:4.8.1")

        implementation("com.google.inject:guice:5.1.0")
        implementation("com.google.inject.extensions:guice-servlet:5.1.0")

        implementation("org.eclipse.jetty:jetty-server:9.4.33.v20201020")
        implementation("org.eclipse.jetty:jetty-servlet:9.4.33.v20201020")

        implementation("javax.ws.rs:javax.ws.rs-api:2.1.1")

        implementation("org.jboss.resteasy:resteasy-guice:4.7.7.Final")
        implementation("org.jboss.resteasy:resteasy-jackson2-provider:4.7.7.Final")

        implementation("com.zaxxer:HikariCP:5.0.1")

        implementation("org.jooq:jooq:3.17.5")
        implementation("org.jooq:jooq-codegen:3.17.5")
        implementation("org.jooq:jooq-meta:3.17.5")

        implementation("org.postgresql:postgresql:42.5.1")

        implementation("org.flywaydb:flyway-core:9.8.3")

        implementation("org.jetbrains:annotations:23.0.0")

        implementation("com.fasterxml.jackson.core:jackson-databind:2.14.1")

        compileOnly("javax.servlet:javax.servlet-api:4.0.1")

        compileOnly("org.projectlombok:lombok:1.18.24")
        annotationProcessor("org.projectlombok:lombok:1.18.24")
    }

    tasks {
        test {
            useJUnitPlatform()
        }
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}