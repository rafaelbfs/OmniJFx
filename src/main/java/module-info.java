module jfxtras {
    requires javafx.controls;
    requires javafx.graphics;
    requires kotlin.stdlib;
    requires annotations;
    //requires org.junit.jupiter.engine;

    opens systems.terranatal.jfxtras;
    opens systems.terranatal.jfxtras.datautils;

    exports systems.terranatal.jfxtras.datautils;

    exports systems.terranatal.jfxtras;
}
