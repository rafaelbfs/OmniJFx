/*
 * Copyright © 2024, Rafael Barros Felix de Sousa @ Terranatal Systems
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice,
 *       this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of {{ project }} nor the names of its contributors
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package systems.terranatal

import gradle.kotlin.dsl.accessors._ef75a60ead13a537a1cba035154c8ec4.publishing
import gradle.kotlin.dsl.accessors._ef75a60ead13a537a1cba035154c8ec4.signing
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType

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
