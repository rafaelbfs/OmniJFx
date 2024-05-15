module omnijfx {
    requires javafx.controls;
    requires javafx.graphics;
  requires java.logging;
  //requires org.junit.jupiter.engine;

    opens systems.terranatal.omnijfx.jfx.builder;
    opens systems.terranatal.omnijfx.jfx.datautils;

    exports systems.terranatal.omnijfx.jfx.builder;
    exports systems.terranatal.omnijfx.jfx.datautils;
}
