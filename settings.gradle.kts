pluginManagement {
    repositories {
        google()
        maven(
            uri("https://maven.google.com")
        )
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    includeBuild("build-logic")
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven(
            uri("https://maven.google.com")
        )
        mavenCentral()
    }
}

rootProject.name = "bookdownloader"
include(":app")
include(":core")
include(":base")
