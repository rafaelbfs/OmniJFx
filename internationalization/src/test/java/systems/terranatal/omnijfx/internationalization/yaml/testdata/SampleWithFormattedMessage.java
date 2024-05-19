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
 *     * Neither the name of com.maddyhome.idea.copyright.pattern.ProjectInfo@65c2f81f nor the names of its contributors
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

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SampleWithFormattedMessage {

  public static class FormattedMessages {
    private String unitsRemaining;

    public String getUnitsRemaining() {
      return unitsRemaining;
    }
  }

  public static class RawMessages {
    private String helloWorld;

    public String getHelloWorld() {
      return helloWorld;
    }
  }

  public static class AppMessages {
    private RawMessages raw;

    private FormattedMessages formatted;

    public RawMessages getRaw() {
      return raw;
    }

    public FormattedMessages getFormatted() {
      return formatted;
    }
  }

  private AppMessages messages;

  public AppMessages getMessages() {
    return messages;
  }

  private static Set<String> TAGS = Stream.of(RawMessages.class, FormattedMessages.class)
      .map(Class::getName).collect(Collectors.toSet());

  public static Constructor makeConstructor(LoaderOptions opts) {
    opts.setTagInspector(tag -> TAGS.contains(tag.getClassName()));
    var constructor = new Constructor(SampleWithFormattedMessage.class, opts);
    var app = new TypeDescription(SampleWithFormattedMessage.class);
    app.addPropertyParameters("messages", AppMessages.class);

    constructor.addTypeDescription(app);

    return constructor;
  }
}
