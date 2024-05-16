package systems.terranatal.omnijfx.internationalization.yaml;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.constructor.Constructor;

public class Sample {

  public static Constructor makeConstructor(LoaderOptions opts) {
    var constructor = new Constructor(Sample.class, opts);
    var app = new TypeDescription(Sample.class);
    app.addPropertyParameters("application", Application.class);

    constructor.addTypeDescription(app);

    return constructor;
  }

  private Application application;

  public Application getApplication() {
    return application;
  }

  public void setApplication(Application application) {
    this.application = application;
  }

  public static class Greetings {
    private String goodMorning;
    private String goodAfternoon;
    private String goodEvening;
    private String hello;
    private String formal;

    public String getGoodMorning() {
      return goodMorning;
    }

    public void setGoodMorning(String goodMorning) {
      this.goodMorning = goodMorning;
    }

    public String getGoodAfternoon() {
      return goodAfternoon;
    }

    public void setGoodAfternoon(String goodAfternoon) {
      this.goodAfternoon = goodAfternoon;
    }

    public String getGoodEvening() {
      return goodEvening;
    }

    public void setGoodEvening(String goodEvening) {
      this.goodEvening = goodEvening;
    }

    public String getHello() {
      return hello;
    }

    public void setHello(String hello) {
      this.hello = hello;
    }

    public String getFormal() {
      return formal;
    }

    public void setFormal(String formal) {
      this.formal = formal;
    }
  }

  public static class Application {
    private String language;

    private Greetings greetings;

    public String getLanguage() {
      return language;
    }

    public void setLanguage(String language) {
      this.language = language;
    }

    public Greetings getGreetings() {
      return greetings;
    }

    public void setGreetings(Greetings greetings) {
      this.greetings = greetings;
    }
  }
}
