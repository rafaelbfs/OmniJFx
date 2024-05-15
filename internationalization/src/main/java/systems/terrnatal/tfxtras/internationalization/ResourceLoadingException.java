package systems.terrnatal.omnijfx.internationalization;

public class ResourceLoadingException extends RuntimeException {
  public final String resourceName;
  public final String source;

  public ResourceLoadingException(String resourceName, String source, Throwable cause) {
    super("%s could not be loaded from %s.".formatted(resourceName, source),
        cause);
    this.resourceName = resourceName;
    this.source = source;
  }

  public ResourceLoadingException(String resourceName, String source, String message) {
    super("%s could not be loaded from %s. %s".formatted(resourceName, source, message));
    this.resourceName = resourceName;
    this.source = source;
  }

  public static ResourceLoadingException fromClasspath(String resourceName, Throwable cause) {
    return new ResourceLoadingException(resourceName, "the classpath", cause);
  }

  public static ResourceLoadingException fromClasspath(String resourceName, String message) {
    return new ResourceLoadingException(resourceName, "the classpath", message);
  }

  public static ResourceLoadingException fromLocalFileSystem(String resourceName, Throwable cause) {
    return new ResourceLoadingException(resourceName, "the host file system", cause);
  }

  public static ResourceLoadingException fromLocalFileSystem(String resourceName, String message) {
    return new ResourceLoadingException(resourceName, "the host file system", message);
  }
}
