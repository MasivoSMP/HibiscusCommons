plugins {
    id("java")
    id("io.canvasmc.weaver.userdev")
}

dependencies {
    paperweight.devBundle("io.canvasmc.pinac", "26.2-local")
    implementation(project(":common"))
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release = 25
    }
    java {
        disableAutoTargetJvm()
        toolchain.languageVersion.set(JavaLanguageVersion.of(25))
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
}