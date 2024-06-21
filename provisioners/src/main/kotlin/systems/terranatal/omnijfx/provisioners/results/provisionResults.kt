package systems.terranatal.omnijfx.provisioners.results

import java.lang.Exception

data class Successful<T: Change>(val changes: List<T>): ProvisionResult(ProvisionStatus.OK)

data class Skipped(val reason: String): ProvisionResult(ProvisionStatus.SKIPPED)

data class Aborted(val reason: String): ProvisionResult(ProvisionStatus.ABORTED)

data class Failed(val reason: String, val exception: Exception?): ProvisionResult(ProvisionStatus.FAILED)

data class Partial(val changes: List<Change>): ProvisionResult(ProvisionStatus.PARTIAL)
