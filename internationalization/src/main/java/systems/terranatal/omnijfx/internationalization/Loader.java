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
 *     * Neither the name of omnijfx nor the names of its contributors
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

/**
 * This class loads files as {@link Reader}s so that it can be used by any subclass of
 * {@link ResourceBundle} to parse its contents into a {@link java.util.Map} of resources.
 */
public interface Loader {

  /**
   * Loads the resource given the raw string file name
   * @param fileName the name of the file according to the loading method, eg. classpath local file
   * @param charset the encoding scheme of the file
   * @return a {@link Reader} for the given file contents
   */
  Reader loadResource(String fileName, Charset charset);

  /**
   * Loads the resource with the path in URL notation
   * @param fileName the name of the file as an URL
   * @param charset the encoding scheme of the file
   * @return a {@link Reader} for the given file contents
   */
  Reader loadResource(URL fileName, Charset charset);

  private static boolean isSystemDefault(Locale locale) {
    return Locale.getDefault().equals(locale);
  }

  /**
   * Builds a {@link Reader} for loading the contents of a resource file within the classpath.
   */
  class Classpath implements Loader {
    /**
     * A {@link ClassLoader} provided by the user through constructor parameters.
     * Defaults to <code>Thread.currentThread().getContextClassLoader()</code> in the default constructor
     */
    public final ClassLoader userClassLoader;

    /**
     * Initializes this Loader with an user-provided {@link ClassLoader}
     * @param userClassLoader a {@link ClassLoader} provided by the user
     */
    public Classpath(ClassLoader userClassLoader) {
      this.userClassLoader = userClassLoader;
    }

    /**
     * Initializes this Loader with the context {@link ClassLoader} given by the current running thread
     */
    public Classpath() {
      this(Thread.currentThread().getContextClassLoader());
    }

    /**
     * Loads the resource using the {@code userClassLoader}
     * @param fileName the name of the file to be loaded
     * @param charset the encoding scheme of the file
     * @return the Reader to the file contents
     * @throws ResourceLoadingException if the file could not be found or opened
     */
    public Reader loadResource(String fileName, Charset charset) {
      InputStream in = userClassLoader.getResourceAsStream(fileName);
      if (in == null) {
        throw ResourceLoadingException.fromClasspath(fileName, "%s not found.".formatted(fileName));
      }
      return new InputStreamReader(in, charset);
    }

    /**
     * Loads the resource represented as a URL using {@link java.nio} API
     * @param fileName the file location as a URL
     * @param charset the encoding scheme of the file
     * @return a {@link Reader} for the given file contents
     * @throws ResourceLoadingException if the URL is malformed or cannot be converted to URI or if file does not exist
     */
    @Override
    public Reader loadResource(URL fileName, Charset charset) {
      try {
        var file = Paths.get(fileName.toURI()).toFile();
        return new InputStreamReader(new FileInputStream(file), charset);
      } catch (URISyntaxException | FileNotFoundException e) {
        throw ResourceLoadingException.fromClasspath(fileName.toString(), e);
      }
    }
  }

  /**
   * A {@link Loader} which loads a file from the host's filesystem by using {@link java.nio} API
   */
  class LocalFile implements Loader {

    /**
     * Loads a file from the host's filesystem.
     *
     * @param fileName the full or relative path to the resource file
     * @param charset the encoding scheme of the file
     * @return the {@link Reader} for the file contents
     * @throws ResourceLoadingException with the underlying {@link IOException} if the system cannot
     *  open the file
     */
    public Reader loadResource(String fileName, Charset charset) {
      try {
        return Files.newBufferedReader(Paths.get(fileName), charset);
      } catch (IOException e) {
        throw ResourceLoadingException.fromLocalFileSystem(fileName, e);
      }
    }

    /**
     * Loads a file represented by the given URL
     * @param fileName the name of the file as a URL
     * @param charset the encoding scheme of the file
     * @return a {@link Reader} for the given file contents
     * @throws ResourceLoadingException if the URL is malformed or cannot be converted to URI or if file does not exist
     */
    @Override
    public Reader loadResource(URL fileName, Charset charset) {
      try {
        var file = Paths.get(fileName.toURI()).toFile();
        return new InputStreamReader(new FileInputStream(file), charset);
      } catch (URISyntaxException | FileNotFoundException e) {
        throw ResourceLoadingException.fromLocalFileSystem(fileName.toString(), e);
      }
    }
  }
}
