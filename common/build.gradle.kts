// Look into the possibility of splitting off the API portion into its own module? (Probably not worth it right now) - 12/22/23

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }

    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        filteringCharset = Charsets.UTF_8.name()
    }
}

dependencies {
    compileOnly("io.canvasmc.pinac:pinac-api:26.2-local")
}
java {
    disableAutoTargetJvm()
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

val migrationCheckSources = sourceSets.create("migrationCheck") {
    compileClasspath += sourceSets.main.get().output + sourceSets.main.get().compileClasspath
    runtimeClasspath += compileClasspath
}

val migrationCheck by tasks.registering(JavaExec::class) {
    classpath = migrationCheckSources.runtimeClasspath
    mainClass.set("me.lojosho.hibiscuscommons.nms.MigrationCheck")
    enableAssertions = true
}
tasks.check { dependsOn(migrationCheck) }