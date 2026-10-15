plugins {
    application
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "es.cesargp.gestorficheros.App"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
