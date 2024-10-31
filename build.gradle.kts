
plugins {
    id("java")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

group = "ru.expanse"
version = "1.0-SNAPSHOT"

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

sourceSets {
    getByName("main").java.srcDir( "${projectDir}/src/main/java")
    getByName("main").resources.srcDir( "${projectDir}/src/main/resources")
    getByName("test").java.srcDir( "${projectDir}/src/test/java")
    getByName("test").resources.srcDir( "${projectDir}/src/test/resources")
}

repositories {
    mavenCentral()
}

dependencies {
    val mapstructVersion: String by project
    val lombokVersion: String by project
    val lombokMapstructBindingVersion: String by project
    val postgresVersion: String by project
    val liquibaseVersion: String by project
    val postgresTestContainerVersion: String by project

    implementation(
        "org.springframework.boot:spring-boot",
        "org.springframework.boot:spring-boot-starter-web",
        "org.springframework.boot:spring-boot-starter-data-jpa",
        "org.springframework.boot:spring-boot-starter-validation",

        "org.mapstruct:mapstruct:${mapstructVersion}",
        "org.projectlombok:lombok-mapstruct-binding:${lombokMapstructBindingVersion}",

        "org.postgresql:postgresql:${postgresVersion}",
        "org.liquibase:liquibase-core:${liquibaseVersion}",
    )

    compileOnly("org.projectlombok:lombok:${lombokVersion}")

    annotationProcessor (
        "org.mapstruct:mapstruct-processor:${mapstructVersion}",
        "org.projectlombok:lombok:${lombokVersion}",
        "org.projectlombok:lombok-mapstruct-binding:${lombokMapstructBindingVersion}"
    )

    testImplementation(
        "org.springframework.boot:spring-boot-starter-test",
        "org.springframework.boot:spring-boot-testcontainers",
        "org.testcontainers:postgresql:${postgresTestContainerVersion}"
    )

    testCompileOnly("org.projectlombok:lombok:${lombokVersion}")

    testAnnotationProcessor("org.projectlombok:lombok:${lombokVersion}")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

fun DependencyHandler.implementation(vararg deps: Any) {
    for (dep in deps) {
        add("implementation", dep)
    }
}

fun DependencyHandler.annotationProcessor(vararg deps: Any) {
    for (dep in deps) {
        add("annotationProcessor", dep)
    }
}

fun DependencyHandler.testImplementation(vararg deps: Any) {
    for (dep in deps) {
        add("testImplementation", dep)
    }
}