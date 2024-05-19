/*
 * Copyright (c) 2024, Rafael Barros Felix de Sousa @ Terranatal Systems
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
 *     * Neither the name of com.maddyhome.idea.copyright.pattern.ProjectInfo@5512a283 nor the names of its contributors
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
