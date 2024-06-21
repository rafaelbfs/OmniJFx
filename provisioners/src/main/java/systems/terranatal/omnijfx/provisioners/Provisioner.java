package systems.terranatal.omnijfx.provisioners;

import systems.terranatal.omnijfx.provisioners.results.ProvisionResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Stream;

public class Provisioner {
  private final Queue<Stream<ProvisioningTask>> tasks;

  public Provisioner(Queue<Stream<ProvisioningTask>> tasks) {
    this.tasks = tasks;
  }

  public List<ProvisionResult> provision(Executor executor) {
    var taskResults = new ArrayList<ProvisionResult>();
    try {
      while (!tasks.isEmpty()) {
        var task = tasks.poll();
        var futures = task.map(pt -> CompletableFuture.supplyAsync(pt::provisionResource, executor));
        var results = futures.parallel().map(CompletableFuture::join).toList();
        taskResults.addAll(results);
        if (results.stream().anyMatch(ProvisionResult::isAbortionStatus)) {
          break;
        }
      }
    } catch (Exception e) {
      throw new IllegalStateException("Provisioning failed", e);
    }
    return taskResults;
  }
}
