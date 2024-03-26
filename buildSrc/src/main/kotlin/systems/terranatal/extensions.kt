package systems.terranatal

import gradle.kotlin.dsl.accessors._ef75a60ead13a537a1cba035154c8ec4.signing
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugins.signing.Sign
import org.gradle.plugins.signing.SigningExtension

typealias SigningExtFun = SigningExtension.() -> MutableList<Sign>

fun Project.signPublication(publicationName: String) {
    val signKeyId = providers.gradleProperty("signing.keyId").get()
    val signKeyringFile = providers.gradleProperty("signing.secretKeyRingFile").get()
    val signKeyPassword = providers.gradleProperty("signing.password").get()

    return signing {
        if (signKeyId.isNotBlank() && signKeyringFile.isNotBlank() && signKeyPassword.isNotBlank()) {
            //useInMemoryPgpKeys(signKeyId, signKeyringFile, signKeyPassword)
            sign(extensions.getByType<PublishingExtension>().publications[publicationName])
        }
    }
}


