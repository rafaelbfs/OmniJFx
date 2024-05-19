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
 *     * Neither the name of com.maddyhome.idea.copyright.pattern.ProjectInfo@3d113fea nor the names of its contributors
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

package systems.terranatal.omnijfx.internationalization.yaml.testdata;

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
