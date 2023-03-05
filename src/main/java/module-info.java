module tjfxtras {
    requires javafx.controls;
    requires javafx.graphics;
    requires kotlin.stdlib;
    requires annotations;
    //requires org.junit.jupiter.engine;

    opens systems.terranatal.tjfxtras;
    opens systems.terranatal.tjfxtras.datautils;

    exports systems.terranatal.tjfxtras.datautils;

    exports systems.terranatal.tjfxtras;
}
