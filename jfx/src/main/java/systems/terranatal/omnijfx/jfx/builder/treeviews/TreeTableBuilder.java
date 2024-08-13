package systems.terranatal.omnijfx.jfx.builder.treeviews;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.util.Callback;
import systems.terranatal.omnijfx.jfx.builder.Initializer;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Helps to build a {@link TreeTableView}.
 *
 * @param <S> the type of each table item
 */
public class TreeTableBuilder<S> extends Initializer<TreeTableView<S>> {

  public static <S> TreeTableBuilder<S> of(TreeItem<S> root) {
    return new TreeTableBuilder<>(new TreeTableView<>(root));
  }

  /**
   * Kept to conform with the superclass constructor.
   * We recommend to use {@link TreeTableBuilder#of(TreeItem)} method instead.
   *
   * @param instance the {@link TreeTableView} to be initialized
   */
  protected TreeTableBuilder(TreeTableView<S> instance) {
    super(instance);
  }

    /**
     * Creates and adds a new column to the table
     * @param columnName the column name
     * @param valueFactory the cell value factory
     * @return this TreeTableBuilder
     * @param <T> the type of the property shown by the cells under the column
     */
  public <T> TreeTableBuilder<S> addColumn(
      String columnName,
      Callback<TreeTableColumn.CellDataFeatures<S, T>, ObservableValue<T>> valueFactory) {

    TreeTableColumn<S, T> col = new TreeTableColumn<>(columnName);
    col.setCellValueFactory(valueFactory);

    instance.getColumns().add(col);
    return this;
  }

    /**
     * Creates and adds a new column to the table
     * @param columnName the column name
     * @param valuePropertyGetter the cell value factory
     * @return
     * @param <T> the type of the property shown by the cells under the column
     */
    public <T> TreeTableBuilder<S> addColumn(
        String columnName,
        Function<S, ObservableValue<T>> valuePropertyGetter) {

        return addColumn(columnName,
            makeCallbackWithGetter(valuePropertyGetter));
    }

  /**
   * Creates and adds a new column to the present (instance) table
   * @param columnName the name opf the column
   * @param columnInitializer a {@link Consumer} which will accept (handle) the just-initialized column
   * @return this builder
   * @param <T> the type of the related property value
   */
  public <T> TreeTableBuilder<S> addColumn(
      String columnName,
      Callback<TreeTableColumn.CellDataFeatures<S, T>, ObservableValue<T>> valueFactory,
      Consumer<TreeTableColumn<S,T>> columnInitializer) {

    TreeTableColumn<S, T> col = new TreeTableColumn<>(columnName);
    col.setCellValueFactory(valueFactory);
    instance.getColumns().add(col);
    columnInitializer.accept(col);

    return this;
  }

  /**
   * Creates a {@link Callback} from the getter lambda passed as parameter
   * @param valuePropertyGetter the getter lambda
   * @return the {@link Callback} derived from the valuePropertyGetter
   * @param <S> the type of item of the table
   * @param <T> the type of value held by the {@link  ObservableValue} returned by the {@code valuePropertyGetter}.
   */
  public static <S, T> Callback<TreeTableColumn.CellDataFeatures<S, T>, ObservableValue<T>>
      makeCallbackWithGetter(Function<S, ObservableValue<T>> valuePropertyGetter) {
    return cdf -> valuePropertyGetter.apply(cdf.getValue().getValue());
  }
}
