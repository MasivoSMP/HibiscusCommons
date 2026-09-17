pluginManagement {
    val userdevVersion: String by settings
    repositories {
        gradlePluginPortal()
        maven("https://maven.canvasmc.io/public")
        maven("https://repo.papermc.io/repository/maven-public/")
    }
    plugins {
        id("io.papermc.paperweight.userdev") version userdevVersion
    }
}

rootProject.name = "HibiscusCommons"
include(
    "common",
    "v1_21_R3",
    "v1_21_R4",
    "v1_21_R5",
    "v1_21_R6",
    "v1_21_R7",
    "v26_1_R1",
    "v26_2_R1",
)