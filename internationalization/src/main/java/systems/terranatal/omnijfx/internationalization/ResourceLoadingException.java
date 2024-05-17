package systems.terranatal.omnijfx.internationalization;

/**
 * Application exception for resource file loading issues.
 */
public class ResourceLoadingException extends RuntimeException {
  /**
   * The name (can be URI string) of the resource attempted to be loaded
   */
  public final String resourceName;
  /**
   * Where the resource was located, ie. in the classpath, host filesystem, network...
   */
  public final String source;

  /**
   * Initializes this Exception
   *
   * @param resourceName the name (can be URI string) of the resource attempted to be loaded
   * @param source where the resource was located, ie. in the classpath, host filesystem, network...
   * @param cause the exception that caused this issue
   */
  public ResourceLoadingException(String resourceName, String source, Throwable cause) {
    super("%s could not be loaded from %s.".formatted(resourceName, source),
        cause);
    this.resourceName = resourceName;
    this.source = source;
  }

  /**
   * Initializes this Exception with a custom message
   *
   * @param resourceName the name (can be URI string) of the resource attempted to be loaded
   * @param source where the resource was located, ie. in the classpath, host filesystem, network...
   * @param message the custom message
   */
  public ResourceLoadingException(String resourceName, String source, String message) {
    super("%s could not be loaded from %s. %s".formatted(resourceName, source, message));
    this.resourceName = resourceName;
    this.source = source;
  }

  /**
   * Factory method that instantiates a {@link ResourceLoadingException} caused by an issue reading a file
   * from the classpath
   *
   * @param resourceName the name (can be URI string) of the resource attempted to be loaded
   * @param cause the {@link Throwable} that caused the issue to load the resource
   * @return the initialized {@link ResourceLoadingException}
   */
  public static ResourceLoadingException fromClasspath(String resourceName, Throwable cause) {
    return new ResourceLoadingException(resourceName, "the classpath", cause);
  }

  /**
   * Factory method that instantiates a {@link ResourceLoadingException} with a custom message
   * during the loading of a resource in the classpath.
   *
   * @param resourceName the name (can be URI string) of the resource attempted to be loaded
   * @param message a custom message
   * @return the initialized {@link ResourceLoadingException}
   */
  public static ResourceLoadingException fromClasspath(String resourceName, String message) {
    return new ResourceLoadingException(resourceName, "the classpath", message);
  }

  /**
   * Factory method that instantiates a {@link ResourceLoadingException} caused by an issue reading a file
   * from the host's filesystem
   *
   * @param resourceName the name (can be URI string) of the resource attempted to be loaded
   * @param cause the {@link Throwable} that caused the issue to load the resource
   * @return the initialized {@link ResourceLoadingException}
   */
  public static ResourceLoadingException fromLocalFileSystem(String resourceName, Throwable cause) {
    return new ResourceLoadingException(resourceName, "the host file system", cause);
  }

  /**
   * Factory method that instantiates a {@link ResourceLoadingException} with a custom message
   * during the loading of a resource in the filesystem.
   *
   * @param resourceName the name (can be URI string) of the resource attempted to be loaded
   * @param message a custom message
   * @return the initialized {@link ResourceLoadingException}
   */
  public static ResourceLoadingException fromLocalFileSystem(String resourceName, String message) {
    return new ResourceLoadingException(resourceName, "the host file system", message);
  }
}
