/*
 * Copyright (c) 2024, Rafael Barros Felix de Sousa @ Terranatal Systems
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice,
 *       this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of omnijfx nor the names of its contributors
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package systems.terranatal.omnijfx.jfx.builder;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * {@link PaneInitializer} specialized inm initializing {@link GridPane}
 */
public class GridInitializer extends PaneInitializer<GridPane>  {
  /**
   * Creates a GridInitializer with the instance inside
   * @param instance a {@link GridPane}
   */
  public GridInitializer(GridPane instance) {
    super(instance);
  }

  /**
   * Creates a GridInitializer using the object returned by calling the supplier parameter
   * @param supplier a {@link Supplier} which returns a {@link GridPane} instance
   */
  public GridInitializer(Supplier<GridPane> supplier) {
    super(supplier);
  }

  /**
   * No-args constructor that creates a {@link GridPane} in its body.
   */
  public GridInitializer() {
    this(new GridPane());
  }

  /**
   * Wraps an instance of {@link GridPane} inside the a {@link GridInitializer}
   * @param instance the instance to be wrapped
   * @return a {@link GridInitializer} with the instance wrapped inside it
   */
  public static GridInitializer wrap(GridPane instance) {
    return new GridInitializer(instance);
  }

  /**
   * Adds a column with the nodes to the {@link GridPane}
   * @param colIndex the column index
   * @param nodes the {@link Node}s to be added
   * @return this {@link GridInitializer}
   * @param <N> any {@link Node} and its subclasses
   */
  @SafeVarargs
  public final <N extends Node> GridInitializer addColumn(int colIndex, N... nodes) {
    if (colIndex < 0 || nodes.length == 0) return this;
    instance.addColumn(colIndex, nodes);
    return this;
  }

  /**
   * Adds a column with the nodes returned by each of the {@link Supplier}s in the arguments
   * to the {@link GridPane}
   * @param colIndex the column index
   * @param nodes {@link Supplier}s of any {@link Node} instance
   * @return this {@link GridInitializer}
   */
  @SafeVarargs
  public final GridInitializer addColumn(int colIndex, Supplier<? extends Node>... nodes) {
    var stream = Stream.of(nodes).map(Supplier::get);
    return addColumn(colIndex, stream.toArray(Node[]::new));
  }

  /**
   * Adds a row with the nodes to the {@link GridPane}
   * @param rowIndex the row index
   * @param nodes the {@link Node}s to be added
   * @return this {@link GridInitializer}
   * @param <N> any {@link Node} and its subclasses
   */
  @SafeVarargs
  public final <N extends Node> GridInitializer addRow(int rowIndex, N... nodes) {
    if (rowIndex < 0 || nodes.length == 0) {
      return this;
    }
    instance.addRow(rowIndex, nodes);
    return this;
  }

  /**
   * Adds a row with the nodes returned by each of the {@link Supplier}s in the arguments
   * to the {@link GridPane}
   * @param rowIndex the row index
   * @param nodes {@link Supplier}s of any {@link Node} instance
   * @return this {@link GridInitializer}
   */
  @SafeVarargs
  public final GridInitializer addRow(int rowIndex, Supplier<? extends Node>... nodes) {
    var stream = Stream.of(nodes).map(Supplier::get);
    return addRow(rowIndex, stream.toArray(Node[]::new));
  }
}
