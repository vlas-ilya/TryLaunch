
plugins {
    alias(libs.plugins.kotlin.jvm)
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(kotlin("test"))
    testImplementation(libs.kotlinx.coroutines.test)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
