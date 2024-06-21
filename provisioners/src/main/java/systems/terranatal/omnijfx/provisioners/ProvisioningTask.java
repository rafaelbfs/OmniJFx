package systems.terranatal.omnijfx.provisioners;


import systems.terranatal.omnijfx.provisioners.ProvisionCheck.Command;
import systems.terranatal.omnijfx.provisioners.ProvisionCheck.End;
import systems.terranatal.omnijfx.provisioners.ProvisionCheck.ProceedToNextCheck;
import systems.terranatal.omnijfx.provisioners.ProvisionCheck.SkipProvisioning;
import systems.terranatal.omnijfx.provisioners.platforms.Platform;
import systems.terranatal.omnijfx.provisioners.results.ProvisionResult;
import systems.terranatal.omnijfx.provisioners.results.ProvisionResult.Aborted;
import systems.terranatal.omnijfx.provisioners.results.ProvisionResult.Failed;
import systems.terranatal.omnijfx.provisioners.results.ProvisionResult.Skipped;

import java.util.Optional;

public interface ProvisioningTask {

  /**
   * Allows platform specific tasks. The default implementation always returns,
   * meaning that the task can be executed at any host. The user just needs to override this
   * method if the task is supposed to run on a specific platform.
   *
   * @param platform the host platform
   * @return true if the platform is supported, false otherwise
   */
  default boolean supportsPlatform(Platform platform) {
    return true;
  }

  /**
   * Allows the user to verify if the resources are already provisioned, in which case this
   * execution can be skipped altogether
   * @return
   */
  boolean alreadyProvisioned();

  /**
   * This method is where all the resource provisioning should be executed.
   * <p><b>Never call the default implementation of {@link ProvisioningTask#provisionResource()}
   * in this method!!! Doing so will cause infinite recursion.</b></p>
   * @return the provision result
   */
  ProvisionResult executeResourceProvisioning();

  default Optional<ProceedToNextCheck> customValidations() {
    return Optional.empty();
  }

  /**
   * Perform basic validations, if platform is supported, if resources are already provisioned,
   * and optional custom validations. In that exact order, the basic validations will short-circuit
   * if one terminates, the next ones will not be executed.
   *
   * @param customValidations optional custom validations
   * @return the End result of the validation
   */
  default End validate(Optional<ProceedToNextCheck> customValidations) {
    var platform = Platform.detectPlatform();

    ProvisionCheck redundantProvisionCheck = () -> {
      if (alreadyProvisioned()) {
        return new SkipProvisioning("Resources of %s are already provisioned"
            .formatted(this.getClass().getSimpleName()));
      }
      return customValidations.isPresent() ? customValidations.get() :
          new End(Command.PROCEED, "Basic validations succeeded");
    };
    ProvisionCheck platformCheck = () ->  supportsPlatform(platform) ?
        new ProceedToNextCheck(redundantProvisionCheck): new SkipProvisioning("Unsupported platform");

    return ProvisionCheck.executeChain(new ProceedToNextCheck(platformCheck));
  }

  /**
   * Perform basic validations (if platform is supported and if resources are already provisioned)
   * and optional custom validations (as defined in {@link ProvisioningTask#customValidations()} )
   * before executing the
   * final resource provisioning.
   *
   * @return the final provisioning result
   */
  default ProvisionResult provisionResource() {
    var validationResult = validate(customValidations());
    switch (validationResult.finalCommand()) {
      case PROCEED: return executeResourceProvisioning();
      case SKIP: return new Skipped(validationResult.reason());
      case ABORT: return new Aborted(validationResult.reason());
      default: return new Failed("Validation yielded a %s result".formatted(validationResult.finalCommand()));
    }
  }

  /**
   * Task specific for any Unix host (<b>including MacOS</b>) and provides a custom implementation
   * for {@link ProvisioningTask#supportsPlatform(Platform)} to this end.
   * Can be used for instance to create a .directory.
   */
  interface AnyUnixProvisioningTask extends ProvisioningTask {
    @Override
    default boolean supportsPlatform(Platform platform) {
      return Platform.MAC.equals(platform) && Platform.UNIX.equals(platform);
    }
  }
}
