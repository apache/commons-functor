/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.functor.core.composite;

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.lang3.Validate;

/**
 * A {@link BinaryFunction BinaryFunction} composed of
 * three binary functions, <i>f</i>, <i>g</i> and <i>h</i>,
 * evaluating the ordered parameters <i>x</i>, <i>y</i>
 * to <code><i>f</i>(<i>g</i>(<i>x</i>,<i>y</i>),<i>h</i>(<i>x</i>,<i>y</i>))</code>.
 * @param <L> the function left argument type.
 * @param <R> the function right argument type.
 * @param <T> the function returned value type.
 * @version $Revision$ $Date$
 */
public class BinaryCompositeBinaryFunction<L, R, T> implements BinaryFunction<L, R, T> {

    /** Base hash integer used to shift hash. */
    private static final int HASH_SHIFT = 4;

    /**
     * Type-remembering Helper.
     *
     * @param <G> the function left argument type.
     * @param <H> the function right argument type.
     */
    private static class Helper<G, H, L, R, T> implements BinaryFunction<L, R, T> {
        /**
         * Global evaluator.
         */
        private BinaryFunction<? super G, ? super H, ? extends T> f;
        /**
         * This function evaluation will be the left argument of main evaluator.
         */
        private BinaryFunction<? super L, ? super R, ? extends G> g;
        /**
         * This function evaluation will be the right argument of main evaluator.
         */
        private BinaryFunction<? super L, ? super R, ? extends H> h;

        /**
         * Create a new Helper.
         * @param f final BinaryFunction to evaluate
         * @param g left preceding BinaryFunction
         * @param h right preceding BinaryFunction
         */
        public Helper(BinaryFunction<? super G, ? super H, ? extends T> f,
                BinaryFunction<? super L, ? super R, ? extends G> g,
                BinaryFunction<? super L, ? super R, ? extends H> h) {
            this.f = f;
            this.g = g;
            this.h = h;
        }

        /**
         * {@inheritDoc}
         */
        public T evaluate(L left, R right) {
            return f.evaluate(g.evaluate(left, right), h.evaluate(left, right));
        }
    }

    /**
     * The helper used for the evaluation.
     */
    private final Helper<?, ?, L, R, T> helper;

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new BinaryCompositeBinaryFunction.
     *
     * @param <G> the main function left argument type.
     * @param <H> the main function right argument type.
     * @param f final BinaryFunction to evaluate
     * @param g left preceding BinaryFunction
     * @param h right preceding BinaryFunction
     */
    public <G, H> BinaryCompositeBinaryFunction(BinaryFunction<? super G, ? super H, ? extends T> f,
            BinaryFunction<? super L, ? super R, ? extends G> g, BinaryFunction<? super L, ? super R, ? extends H> h) {
        this.helper = new Helper<G, H, L, R, T>(
                Validate.notNull(f, "final BinaryFunction argument must not be null"),
                Validate.notNull(g, "left preceding BinaryFunction argument must not be null"),
                Validate.notNull(h, "right preceding BinaryFunction argument must not be null")
        );
    }

    // function interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public final T evaluate(L left, R right) {
        return helper.evaluate(left, right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BinaryCompositeBinaryFunction<?, ?, ?>)) {
            return false;
        }
        BinaryCompositeBinaryFunction<?, ?, ?> that = (BinaryCompositeBinaryFunction<?, ?, ?>) obj;
        return this.helper.f.equals(that.helper.f)
                && this.helper.g.equals(that.helper.g)
                && this.helper.h.equals(that.helper.h);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "BinaryCompositeBinaryFunction".hashCode();
            hash <<= HASH_SHIFT;
            hash ^= helper.f.hashCode();
            hash <<= HASH_SHIFT;
            hash ^= helper.g.hashCode();
            hash <<= HASH_SHIFT;
            hash ^= helper.h.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BinaryCompositeBinaryFunction<" + helper.f + ";" + helper.g + ";" + helper.h + ">";
    }

}
