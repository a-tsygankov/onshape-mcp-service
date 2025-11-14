plugins {
    id("org.openapi.generator") version "7.8.0"
}

dependencies {
    implementation("org.springframework:spring-webflux:6.1.8")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.0")
}

val openApiOutputDir = "$buildDir/generated-sources/openapi"

sourceSets {
    main {
        java {
            srcDir(openApiOutputDir)
        }
    }
}

tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("generateOnshapeClient") {
    generatorName.set("java")
    inputSpec.set("$projectDir/src/main/openapi/onshape-openapi.yaml")
    outputDir.set(openApiOutputDir)
    apiPackage.set("com.example.onshape.api")
    invokerPackage.set("com.example.onshape.invoker")
    modelPackage.set("com.example.onshape.model")
    configOptions.set(
        mapOf(
            "dateLibrary" to "java8",
            "library" to "webclient",
            "useJakartaEe" to "true"
        )
    )
}

tasks.named("compileJava") {
    dependsOn("generateOnshapeClient")
}
