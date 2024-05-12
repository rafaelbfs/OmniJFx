module tfxtras {
    requires javafx.controls;
    requires javafx.graphics;
  requires java.logging;
  //requires org.junit.jupiter.engine;

    opens systems.terranatal.tfxtras.jfx.builder;
    opens systems.terranatal.tfxtras.jfx.datautils;

    exports systems.terranatal.tfxtras.jfx.builder;
    exports systems.terranatal.tfxtras.jfx.datautils;
}
