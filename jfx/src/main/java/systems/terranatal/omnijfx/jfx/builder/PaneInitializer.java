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
import javafx.scene.layout.Pane;

import java.util.function.Supplier;

/**
 * Class deriving {@link Initializer} with specializations for {@link Pane}s
 * @param <P> a subtype of {@link Pane}
 */
public class PaneInitializer<P extends Pane> extends Initializer<P> {
    /**
     * Constructs an initializer with the given instance
     * @param instance the instance to be initialized
     */
    public PaneInitializer(P instance) {
        super(instance);
    }

    /**
     * Does the same as {@link PaneInitializer#PaneInitializer(Pane)} but with a
     * {@link Supplier} instead
     * @param supplier the {@link Supplier}, which can even be an object of this class
     */
    public PaneInitializer(Supplier<P> supplier) {
        super(supplier);
    }

    /**
     * Adds a child to the {@link Initializer#instance}
     * @param child the node to be added
     * @return this initializer
     */
    public PaneInitializer<P> addChild(Node child) {
        instance.getChildren().add(child);
        return this;
    }

    /**
     * Adds the child returned by the given {@link Supplier} to the {@link Initializer#instance}
     * @param child the child {@link Supplier} which can be even an {@link Initializer} since it
     *              implements that interface
     * @return this {@link PaneInitializer}
     * @param <N> any JavaFX {@link Node} type
     */
    public <N extends Node> PaneInitializer<P> addChild(Supplier<N> child) {
        return addChild(child.get());
    }
}
