dependencies {
    implementation("org.apache.httpcomponents.client5:httpclient5:5.3.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    implementation(project(":geometry-core"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.0")
}
