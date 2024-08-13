package systems.terranatal.omnijfx.kfx.treeviews

import javafx.beans.value.ObservableValue
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeTableColumn
import javafx.scene.control.TreeTableView

fun <S> treeTable(init: TreeTableView<S>.() -> Unit): TreeTableView<S> {
  val treeView = TreeTableView<S>()
  init(treeView)
  return treeView
}

fun <S> treeTable(root: TreeItem<S>, init: TreeTableView<S>.() -> Unit): TreeTableView<S> {
  val treeView = TreeTableView<S>(root)
  init(treeView)
  return treeView
}

fun <S, T> TreeTableColumn<S, T>.valueFactoryFrom(getter: (S) -> ObservableValue<T>) =
  setCellValueFactory {cdf -> getter(cdf.value.value)}


fun <S, T> TreeTableView<S>.column(name: String, init: TreeTableColumn<S, T>.() -> Unit) {
  val column = TreeTableColumn<S, T>(name)
  init(column)
  columns.add(column)
}

fun <S, T> TreeTableView<S>.addColumn(name: String, getter: (S) -> ObservableValue<T>) {
  val column = TreeTableColumn<S, T>(name)
  column.valueFactoryFrom(getter)
  columns.add(column)
}

fun <S> treeItem(item: S, init: TreeItem<S>.() -> Unit = { } ): TreeItem<S> {
  val treeItem = TreeItem(item)
  init(treeItem)
  return treeItem
}

fun <S> TreeItem<S>.addChild(item: TreeItem<S>) = children.add(item)

fun <S> TreeItem<S>.addChild(item: S, init: TreeItem<S>.() -> Unit = { }) = addChild(treeItem(item, init))
