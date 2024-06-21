package systems.terranatal.omnijfx.provisioners.tasks;

import systems.terranatal.omnijfx.provisioners.ProvisioningTask;
import systems.terranatal.omnijfx.provisioners.results.Change.DirectoryCreated;
import systems.terranatal.omnijfx.provisioners.results.ProvisionResult;
import systems.terranatal.omnijfx.provisioners.results.ProvisionResult.Failed;
import systems.terranatal.omnijfx.provisioners.results.ProvisionResult.Partial;
import systems.terranatal.omnijfx.provisioners.results.ProvisionResult.Skipped;
import systems.terranatal.omnijfx.provisioners.results.ProvisionResult.Successful;

import java.nio.file.Path;
import java.util.List;

public record CreateDirectory(Path appDir) implements ProvisioningTask {

  @Override
  public boolean alreadyProvisioned() {
    return appDir.toFile().isDirectory();
  }

  @Override
  public ProvisionResult executeResourceProvisioning() {
    var file = appDir.toFile();
    try {
      if (file.exists()) {
        return new Skipped("There is already a file in the location");
      }
      var successful = file.mkdirs();
      if (successful) {
        return new Successful(List.of(new DirectoryCreated(appDir.toString())));
      }
      return new Partial(List.of(new DirectoryCreated(appDir.normalize().toString())));
    } catch (Exception e) {
      return new Failed("Exception thrown while creating app directory", e);
    }
  }

  public static Path homeDir() {
    return Path.of(System.getProperty("user.home"));
  }

  /**
   * Creates an App Data Directory within a Unix Home directory it extends {@link CreateDirectory}
   * through composition.
   */
  public class CreateDirectoryInUnixUser
      implements AnyUnixProvisioningTask {
    final CreateDirectory createDirectory;

    /**
     *
     * @param appDir
     */
    public CreateDirectoryInUnixUser(String appDir) {
      createDirectory = new CreateDirectory(homeDir().resolve(appDir));
    }

    @Override
    public boolean alreadyProvisioned() {
      return createDirectory.alreadyProvisioned();
    }

    @Override
    public ProvisionResult executeResourceProvisioning() {
      return createDirectory.executeResourceProvisioning();
    }
  }
}
