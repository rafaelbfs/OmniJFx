package systems.terranatal.omnijfx.internationalization;

import java.util.logging.Logger;

public class Logging {
  private static final Logger LOGGER = Logger.getLogger(Logging.class.getPackageName());

  public static Logger logger() {
    return LOGGER;
  }
}
