package systems.terranatal.omnijfx.kfx

import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.Scene
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeTableView
import javafx.stage.Stage
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.testfx.framework.junit5.ApplicationExtension
import org.testfx.framework.junit5.Start
import systems.terranatal.omnijfx.kfx.extensions.child
import systems.terranatal.omnijfx.kfx.properties.FxProperty
import systems.terranatal.omnijfx.kfx.properties.getValue
import systems.terranatal.omnijfx.kfx.properties.setValue
import systems.terranatal.omnijfx.kfx.treeviews.addChild
import systems.terranatal.omnijfx.kfx.treeviews.addColumn
import systems.terranatal.omnijfx.kfx.treeviews.treeItem
import systems.terranatal.omnijfx.kfx.treeviews.treeTable

@ExtendWith(ApplicationExtension::class)
class TestTreeTable {
  val bigSize = 500000000

  class FileEntry(val nameProperty: FxProperty<String?>, val sizeProperty: SimpleIntegerProperty) {
    constructor(name: String, size: Int = 0) : this(FxProperty(name), SimpleIntegerProperty(size))

    var name by nameProperty
    var size by sizeProperty
  }

  lateinit var treeTable: TreeTableView<FileEntry>
  lateinit var root: TreeItem<FileEntry>

  @Start
  fun start(stage: Stage) {
    root = treeItem(FileEntry("/")) {
      addChild(FileEntry("etc/"))
      addChild(FileEntry("usr/")) {
        addChild(FileEntry("local/")) {
          addChild(FileEntry(".bigHiddenFile", bigSize))
        }
      }
      addChild(FileEntry("home/")) {
        addChild(FileEntry("alice/")) {
          addChild(FileEntry(".zshrc", 1024)) {}
        }
      }
    }
    treeTable = treeTable(root) {
      addColumn("File Name", FileEntry::nameProperty)
      addColumn("File size", FileEntry::sizeProperty)
    }

    val scene = Scene(Panes.vbox { child(treeTable) }, 800.0, 600.0)
  }

  @Test
  public fun test() {
    val rootFile = treeTable.root
    assertEquals("/", rootFile.value.name)
    val etc = rootFile.children[0].value
    assertEquals("etc/", etc.name)
    val usr = rootFile.children[1]
    assertEquals("usr/", usr.value.name)
    val local = usr.children[0]
    assertEquals("local/", local.value.name)
    val bigFile = local.children[0].value
    assertEquals(".bigHiddenFile", bigFile.name)
    assertEquals(bigSize, bigFile.size)
  }
}