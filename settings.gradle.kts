pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "HomeShopping"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:data")
include(":core:domain")
include(":core:presentation:ui")
include(":core:presentation:designsystem")
include(":login:presentation")
include(":login:domain")
include(":welcome:presentation")
include(":register:presentation")
include(":register:domain")
include(":home:domain")
include(":home:presentation")
include(":home:data")
include(":user:presentation")
include(":user:domain")
include(":commerce")
include(":commerce:presentation")
include(":commerce:domain")
include(":products")
include(":products:domain")
include(":products:presentation")
include(":add-product")
include(":add-product:domain")
include(":add-product:presentation")
include(":core:testing")
