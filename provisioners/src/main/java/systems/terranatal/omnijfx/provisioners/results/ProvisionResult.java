package systems.terranatal.omnijfx.provisioners.results;

import java.util.List;

public interface ProvisionResult {
  default boolean isAbortionStatus() {
    return false;
  }

  public enum ProvisionStatus {
    OK, SKIPPED, FAILED, PARTIAL, ABORTED;
  }

  record Successful(List<Change> changes) implements ProvisionResult {}

  record Aborted(String reason) implements ProvisionResult {

    @Override
    public boolean isAbortionStatus() {
      return true;
    }
  }

  record Failed(String reason, Throwable error) implements ProvisionResult {
    public Failed(String reason) {
      this(reason, null);
    }
    public Failed(Throwable error) {
      this(error.getClass().getSimpleName() + "has been thrown", error);
    }
    @Override
    public boolean isAbortionStatus() {
      return true;
    }
  }

  record Skipped(String reason) implements ProvisionResult {}

  record Partial(List<Change> changes) implements ProvisionResult {}
}
