package systems.terranatal.omnijfx.jfx.builder.treeviews;

import javafx.scene.control.TreeItem;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Helps to create a hierarchy of tree items, which in their turn will contain
 * an item of type S
 * @param <S> the type of the item in {@link TreeItem}
 */
public class TreeHierarchyBuilder<S> implements Supplier<TreeItem<S>> {

  private final TreeItem<S> instance;

  /**
   * Convenience method that can be statically imported to make the instantiation of a
   * {@link TreeHierarchyBuilder} more idiomatic.
   *
   * @param instance the entity which will be wrapped by a {@link TreeItem}
   *                 which is wrapped in this {@link TreeHierarchyBuilder}
   * @return {@link TreeHierarchyBuilder} wrapping the passed entity
   * @param <S> the type of the entity which will be wrapped
   */
  public static <S> TreeHierarchyBuilder<S> item(S instance) {
    return new TreeHierarchyBuilder<>(instance);
  }

  /**
   * Default constructor that creates a {@link TreeHierarchyBuilder} with a {@link TreeItem}
   * containing the given entity.
   * @param instance the entity to be wrapped inside the  {@link TreeItem} and the new {@link TreeHierarchyBuilder}
   */
  protected TreeHierarchyBuilder(S instance) {
    this.instance = new TreeItem<>(instance);
  }

  /**
   * Adds several {@link TreeItem}s to the instance being built.
   * @param children the child {@link TreeItem}s wrapped inside {@link TreeHierarchyBuilder}s
   * @return this builder
   */
  @SafeVarargs
  public final TreeHierarchyBuilder<S> addChildren(TreeHierarchyBuilder<S>... children) {
    instance.getChildren().addAll(Stream.of(children).map(TreeHierarchyBuilder::get).toList());

    return this;
  }

  /**
   * Adds a single {@link TreeItem} (returned by calling {@link TreeHierarchyBuilder#get()} on the child parameter)
   * to the instance being built.
   * @param child the {@link TreeHierarchyBuilder} containing the child to be added
   * @return this builder
   */
  public final TreeHierarchyBuilder<S> addChild(TreeHierarchyBuilder<S> child) {
    instance.getChildren().add(child.get());

    return this;
  }

  /**
   * Returns the instance which was being built by this object.
   */
  @Override
  public TreeItem<S> get() {
    return instance;
  }
}
