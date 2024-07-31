plugins {
    id("template.android.compose.library")
    id("template.android.hilt")
    id("template.android.lib")
}

android {
    namespace = "ir.vahidmohammadisan.bookdownloader.core"

    defaultConfig {
        buildConfigField("String", "BOOKS_MAIN_API", "\"https://libgen.is/\"")
        buildConfigField("String", "BOOKS_SECOND_API", "\"http://library.lol/\"")
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material3)
    implementation(libs.hilt)
    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlin.serialization)
    implementation(libs.kotlin.serialization.converter)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.navigation)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.timber)
    androidTestImplementation(libs.bundles.common.android.test)

    ksp(libs.hilt.compiler)
    //kspAndroidTest(libs.test.android.hilt.compiler)

    //detektPlugins(libs.detekt.compose.rules)
}
