package io.loperilla.convention.io.loperilla.convention.utils

@Deprecated("Use MyConfiguration instead")
object MyConfiguration  {
    const val baseProjectName = "io.loperilla"
    const val configCompileSdkVersion = 34
    const val configTargetSdkVersion = 34
    const val configMinSdkVersion = 26
    const val myApplicationIdConfig = "$baseProjectName.homeshopping"
    const val configVersionCode = 1
    const val configVersionName = "1.0"

    object Modules {
        const val COREUI = ":core-ui"
        const val DATA = ":data"
        const val ONB_DOMAIN = ":domain"
        const val ONB_PRESENTATION = ":presentation"
    }

    object MAP_MODULES {
        val DOMAIN = mapOf("path" to Modules.ONB_DOMAIN)
        val DATA = mapOf("path" to Modules.DATA)
    }
}