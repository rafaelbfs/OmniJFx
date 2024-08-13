package systems.terranatal.omnijfx.jfx.builder.treeviews;

import javafx.beans.property.*;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import systems.terranatal.omnijfx.jfx.builder.Builders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static systems.terranatal.omnijfx.jfx.builder.treeviews.TreeHierarchyBuilder.item;

@ExtendWith(ApplicationExtension.class)
public class TestTreeTableBuilder {
  private static final LocalDate now = LocalDate.now();
  private static final LocalDate dt3DaysAgo = LocalDate.now().minusDays(3);

  public static record FileEntry(Property<String> name, Property<String> lastModified, IntegerProperty size) {
    public FileEntry(String name, LocalDate lastModified, int size) {
      this(new SimpleStringProperty(name),
          new SimpleStringProperty(lastModified.format(DateTimeFormatter.ISO_LOCAL_DATE)),
          new SimpleIntegerProperty(size));
    }
  }

  public static final TreeItem<FileEntry> hierarchy = new TreeHierarchyBuilder<>(
      new FileEntry("/", LocalDate.now().minusDays(5), 0)
  ).addChildren(
      item(new FileEntry("etc/", dt3DaysAgo, 0)),
      item(new FileEntry("usr/", dt3DaysAgo, 0))
          .addChild(item(new FileEntry("home/", dt3DaysAgo, 0))
              .addChild(item(new FileEntry("bob/", dt3DaysAgo, 0))
                  .addChild(item(new FileEntry(".zshrc", now, 499)))
                  .addChild(item(new FileEntry(".profile", now, 1024))))
              .addChild(item(new FileEntry("alice/", dt3DaysAgo, 0)).addChildren(
                  item(new FileEntry(".zshrc", now, 499)),
                  item(new FileEntry(".profile", now, 512)),
                  item(new FileEntry(".commands", now, 1024))))
          ).addChild(item(new FileEntry("local/", dt3DaysAgo, 0))),
      item(new FileEntry("opt/", dt3DaysAgo, 0))
  ).get();

  private TreeTableView<FileEntry> treeTable;

  private Scene makeTestScene() {
    treeTable = TreeTableBuilder.of(hierarchy)
        .addColumn("File Name", FileEntry::name)
        .addColumn("Last Modified", FileEntry::lastModified)
        .addColumn("Size", FileEntry::size).get();

    return new Scene(Builders.pane(new FlowPane()).addChild(treeTable).get(),
        720.0, 800.0);
  }

  @Start
  public void start(Stage stage) {
    stage.setScene(makeTestScene());
    stage.show();
  }

  @Test
  public void test() {
    var root = treeTable.getTreeItem(0);
    Assertions.assertEquals("/", root.getValue().name.getValue());
    Assertions.assertEquals(now.minusDays(5),
        LocalDate.parse(root.getValue().lastModified.getValue(), DateTimeFormatter.ISO_LOCAL_DATE));
    var usr = root.getChildren().get(1);
    Assertions.assertEquals("usr/", usr.getValue().name.getValue());
    var home = usr.getChildren().get(0);
    Assertions.assertEquals("home/", home.getValue().name.getValue());
    var alice = home.getChildren().get(1);
    Assertions.assertEquals("alice/", alice.getValue().name.getValue());
    var dotCommands = alice.getChildren().get(2);
    Assertions.assertEquals(".commands", dotCommands.getValue().name.getValue());
    Assertions.assertTrue(dotCommands.getChildren().isEmpty());
  }

}
