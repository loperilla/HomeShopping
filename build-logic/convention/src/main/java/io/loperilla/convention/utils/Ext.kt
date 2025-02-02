package io.loperilla.convention.utils

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType


/*****
 * Project: HomeShopping
 * From: plugins.utils
 * Created By Manuel Lopera on 1/2/25 at 12:38
 * All rights reserved 2025
 */

val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")
