package systems.terranatal.omnijfx.jfx;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import systems.terranatal.omnijfx.jfx.builder.Builders;
import systems.terranatal.omnijfx.jfx.builder.treeviews.TestTreeTableBuilder;
import systems.terranatal.omnijfx.jfx.builder.treeviews.TreeTableBuilder;

import java.util.function.Function;

import static systems.terranatal.omnijfx.jfx.builder.treeviews.TestTreeTableBuilder.FileEntry;
import static systems.terranatal.omnijfx.jfx.builder.treeviews.TreeTableBuilder.makeCallbackWithGetter;

public class DebugMain extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("OmniJFX");

    var treeTable = TreeTableBuilder.of(TestTreeTableBuilder.hierarchy)
        .addColumn("File Name", makeCallbackWithGetter(FileEntry::name), col -> {
          col.setMinWidth(800);
        })
        .addColumn("Last Modified", FileEntry::lastModified)
        .addColumn("Size", FileEntry::size)
        .get();

    var scene = new Scene(Builders.pane(new FlowPane()).addChild(treeTable).get(),
        720.0, 800.0);

    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
