@file:Suppress("OPT_IN_USAGE")

import org.jetbrains.compose.ComposeBuildConfig.composeVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    kotlin("multiplatform")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
//    id("maven-publish")
//    id("signing")
    id("com.vanniktech.maven.publish")
}

group = "org.burnoutcrew.composereorderable"
version = "0.9.7-syk3"

kotlin {
    jvmToolchain(17)

    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    js(IR) {
        browser()
//        binaries.executable()
    }
    wasmJs {
        browser()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.foundation)
                implementation(compose.animation)
                implementation("org.jetbrains.compose.ui:ui-util:${composeVersion}")
            }
        }
    }
}

//val javadocJar = tasks.register("javadocJar", Jar::class.java) {
//    archiveClassifier.set("javadoc")
//}

publishing {
    publications {
        repositories {
            maven {
                val localProperties: Properties = Properties()
                val localPropertiesFile: File = rootProject.file("local.properties")
                if (localPropertiesFile.isFile) {
                    localProperties.load(localPropertiesFile.reader())
                }

                name = "SykSh"
                url = uri("https://maven.syk.sh/releases")
                credentials(PasswordCredentials::class) {
                    this.username = localProperties["publishing.syksh.user"] as String?
                    this.password = localProperties["publishing.syksh.key"] as String?
                }
            }
//            maven {
//                name="oss"
//                val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
//                val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
//                url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
//                credentials {
//                    username = extra.properties.getOrDefault("ossrh.Username", "") as String
//                    password = extra.properties.getOrDefault("ossrh.Password", "") as String
//                }
//            }
        }
    }
    publications {
        withType<MavenPublication> {
//            artifact(javadocJar)
            pom {
                name.set("ComposeReorderable")
                description.set("Reorderable Compose LazyList")
                licenses {
                    license {
                        name.set("Apache-2.0")
                        url.set("https://opensource.org/licenses/Apache-2.0")
                    }
                }
                url.set("https://github.com/aclassen/ComposeReorderable")
                issueManagement {
                    system.set("Github")
                    url.set("https://github.com/aclassen/ComposeReorderable/issues")
                }
                scm {
                    connection.set("https://github.com/aclassen/ComposeReorderable.git")
                    url.set("https://github.com/aclassen/ComposeReorderable")
                }
                developers {
                    developer {
                        name.set("Andre Claßen")
                        email.set("andreclassen1337@gmail.com")
                    }
                }
            }
        }
    }
}

//signing {
//    sign(publishing.publications)
//}
