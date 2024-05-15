package systems.terranatal

import gradle.kotlin.dsl.accessors._ef75a60ead13a537a1cba035154c8ec4.publishing
import gradle.kotlin.dsl.accessors._ef75a60ead13a537a1cba035154c8ec4.signing
import org.gradle.api.Project
import org.gradle.api.publish.PublicationContainer
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugins.signing.Sign
import org.gradle.plugins.signing.SigningExtension

internal fun Project.allSignParametersPresent(): Boolean {
  val signKeyId = providers.gradleProperty("signing.keyId").isPresent
  val signKeyringFile = providers.gradleProperty("signing.secretKeyRingFile").isPresent
  val signKeyPassword = providers.gradleProperty("signing.password").isPresent

  return signKeyId && signKeyringFile && signKeyPassword
}


fun Project.signPublication(publicationName: String) {
  if (!allSignParametersPresent()) {
    return
  }

  return signing {
      sign(extensions.getByType<PublishingExtension>().publications[publicationName])
  }
}

fun Project.generateMvnPublication(publicationName: String, bundleName: String) {
  if (!allSignParametersPresent()) {
    return
  }
  publishing {
    publications {
      create<MavenPublication>(publicationName) {
        from(components["java"])

        pom {
          name.set(bundleName)
          description.set("Utility helpers to create JavaFX components using Java")
          url.set("https://github.com/rafaelbfs/omnijfx/jfx")
          licenses {
            license {
              name.set("MIT License")
              url.set("https://opensource.org/license/mit")
            }
          }
          scm {
            connection.set("scm:git:git://github.com/rafaelbfs/omnijfx.git")
            developerConnection.set("scm:git:ssh://github.com/rafaelbfs/omnijfx.git")
            url.set("https://github.com/rafaelbfs/omnijfx/blob/main/")
          }
          developers {
            developer {
              id.set("rafaelbfs")
              name.set("Rafael B F de Sousa")
              email.set("rafael@terranatal.co")
              organization.set("Terranatal Systems")
            }
          }
        }
      }
      signPublication(publicationName)
    }
  }
}
