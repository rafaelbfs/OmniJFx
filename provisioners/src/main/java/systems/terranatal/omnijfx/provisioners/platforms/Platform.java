package systems.terranatal.omnijfx.provisioners.platforms;

public enum Platform {
  /**
   * Returned in case the platform cannot be resolved
   */
  UNKNOWN,
  /**
   * Any MacOS, no iOS
   */
  MAC,
  /**
   * Any Windows
   */
  WINDOWS,
  /**
   * Can be Linux, BSD and other types of Unix. No Android or ChromeOS
   */
  UNIX;

  public static Platform detectPlatform() {
    var osName = System.getProperty("os.name").toLowerCase();

    if (osName.contains("win")) {
      return WINDOWS;
    }
    if (osName.contains("mac")) {
      return MAC;
    }
    if (osName.contains("nix") || osName.contains("nux")
        || osName.contains("aix")
        || osName.contains("bsd")) {
      return UNIX;
    }
    return UNKNOWN;
  }
}
