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

import java.io.Serializable;

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.Function;
import org.apache.commons.lang3.Validate;

/**
 * A {@link BinaryFunction BinaryFunction} composed of
 * one binary function, <i>f</i>, and two
 * functions, <i>g</i> and <i>h</i>,
 * evaluating the ordered parameters <i>x</i>, <i>y</i>
 * to <code><i>f</i>(<i>g</i>(<i>x</i>),<i>h</i>(<i>y</i>))</code>.
 * <p>
 * Note that although this class implements
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if all the
 * underlying functors are.  Attempts to serialize
 * an instance whose delegates are not all
 * <code>Serializable</code> will result in an exception.
 * </p>
 * @param <L> the left argument type.
 * @param <R> the right argument type.
 * @param <T> the returned value type.
 * @version $Revision: 1345136 $ $Date: 2012-06-01 09:47:06 -0300 (Fri, 01 Jun 2012) $
 */
public class CompositeBinaryFunction<L, R, T> implements BinaryFunction<L, R, T>, Serializable {

    /**
     * serialVersionUID declaration.
     */
    private static final long serialVersionUID = 264219357293822629L;

    /** Base hash integer used to shift hash. */
    private static final int HASH_SHIFT = 4;

    /**
     *
     *
     * @param <G> the adapted function left argument type.
     * @param <H> the adapted function right argument type.
     * @param <L> the left argument type.
     * @param <R> the right argument type.
     * @param <T> the returned value type.
     */
    private static class Helper<G, H, L, R, T> implements BinaryFunction<L, R, T>, Serializable {
        /**
         * serialVersionUID declaration.
         */
        private static final long serialVersionUID = 4513309646430305164L;
        /**
         * The adapted function to receive <code>(output(g), output(h))</code>.
         */
        private BinaryFunction<? super G, ? super H, ? extends T> f;
        /**
         * The adapted left function.
         */
        private Function<? super L, ? extends G> g;
        /**
         * The adapted right function.
         */
        private Function<? super R, ? extends H> h;

        /**
         * Create a new Helper.
         * @param f BinaryFunction to receive <code>(output(g), output(h))</code>
         * @param g left Function
         * @param h right Function
         */
        public Helper(BinaryFunction<? super G, ? super H, ? extends T> f, Function<? super L, ? extends G> g,
                Function<? super R, ? extends H> h) {
            this.f = f;
            this.g = g;
            this.h = h;
        }

        /**
         * {@inheritDoc}
         */
        public T evaluate(L left, R right) {
            return f.evaluate(g.evaluate(left), h.evaluate(right));
        }
    }

    /**
     * The adapted helper.
     */
    private final Helper<?, ?, L, R, T> helper;

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new CompositeBinaryFunction.
     *
     * @param <G> the adapted function left argument type.
     * @param <H> the adapted function right argument type.
     * @param f BinaryFunction to receive <code>(output(g), output(h))</code>
     * @param g left Function
     * @param h right Function
     */
    public <G, H> CompositeBinaryFunction(BinaryFunction<? super G, ? super H, ? extends T> f,
            Function<? super L, ? extends G> g, Function<? super R, ? extends H> h) {
        this.helper = new Helper<G, H, L, R, T>(
                Validate.notNull(f, "BinaryFunction must not be null"),
                Validate.notNull(g, "left Function must not be null"),
                Validate.notNull(h, "right Function must not be null")
        );
    }

    // function interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public T evaluate(L left, R right) {
        return helper.evaluate(left, right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object that) {
        return that == this || (that instanceof CompositeBinaryFunction<?, ?, ?>
                                    && equals((CompositeBinaryFunction<?, ?, ?>) that));
    }

    /**
     * Learn whether a given CompositeBinaryFunction is equal to this.
     * @param that CompositeBinaryFunction to test
     * @return boolean
     */
    public boolean equals(CompositeBinaryFunction<?, ?, ?> that) {
        return null != that
                && helper.f.equals(that.helper.f)
                && helper.g.equals(that.helper.g)
                && helper.h.equals(that.helper.h);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "CompositeBinaryFunction".hashCode();
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
        return "CompositeBinaryFunction<" + helper.f + ";" + helper.g + ";" + helper.h + ">";
    }

}
