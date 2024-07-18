plugins {
    application
    antlr
}

group = "pascal"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    antlr("org.antlr:antlr4:4.13.1")
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass = "pascal.lf.Main"
}

tasks.generateGrammarSource {
    val outputPackage = "${project.group}.${project.name}.parser".replace("-", "")
    outputDirectory = file("${outputDirectory}/${outputPackage.replace(".", "/")}")
    arguments.addAll(listOf("-visitor", "-package", outputPackage))
}
