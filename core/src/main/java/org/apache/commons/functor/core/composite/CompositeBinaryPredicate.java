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

import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.Function;
import org.apache.commons.lang3.Validate;

/**
 * A {@link BinaryPredicate BinaryPredicate} composed of
 * one binary predicate, <i>p</i>, and two
 * functions, <i>f</i> and <i>g</i>,
 * evaluating the ordered parameters <i>x</i>, <i>y</i>
 * to <code><i>p</i>(<i>f</i>(<i>x</i>),<i>g</i>(<i>y</i>))</code>.
 * @param <L> the left argument type.
 * @param <R> the right argument type.
 * @version $Revision: 1345136 $ $Date: 2012-06-01 09:47:06 -0300 (Fri, 01 Jun 2012) $
 */
public class CompositeBinaryPredicate<L, R> implements BinaryPredicate<L, R> {

    /** Base hash integer used to shift hash. */
    private static final int HASH_SHIFT = 4;

    /**
     * Internal helper.
     *
     * @param <G> right function type.
     * @param <H> right function type.
     * @param <L> left function type.
     * @param <R> left function type.
     */
    private static class Helper<G, H, L, R> implements BinaryPredicate<L, R> {
        /**
         * BinaryPredicate to test <i>output(</i><code>f</code><i>), output(</i><code>g</code><i>)</i>.
         */
        private BinaryPredicate<? super G, ? super H> f;
        /**
         * left Function.
         */
        private Function<? super L, ? extends G> g;
        /**
         * right Function.
         */
        private Function<? super R, ? extends H> h;

        /**
         * Create a new Helper.
         * @param f BinaryPredicate to test <i>output(</i><code>f</code><i>), output(</i><code>g</code><i>)</i>
         * @param g left Function
         * @param h right Function
         */
        public Helper(BinaryPredicate<? super G, ? super H> f, Function<? super L, ? extends G> g,
                Function<? super R, ? extends H> h) {
            this.f = f;
            this.g = g;
            this.h = h;
        }

        /**
         * {@inheritDoc}
         */
        public boolean test(L left, R right) {
            return f.test(g.evaluate(left), h.evaluate(right));
        }
    }

    // attributes
    // ------------------------------------------------------------------------
    /**
     * The adapted helper.
     */
    private final Helper<?, ?, L, R> helper;

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new CompositeBinaryPredicate.
     *
     * @param <G> right function type.
     * @param <H> right function type.
     * @param f BinaryPredicate to test <i>output(</i><code>f</code><i>), output(</i><code>g</code><i>)</i>
     * @param g left Function
     * @param h right Function
     */
    public <G, H> CompositeBinaryPredicate(final BinaryPredicate<? super G, ? super H> f,
            final Function<? super L, ? extends G> g, final Function<? super R, ? extends H> h) {
        helper = new Helper<G, H, L, R>(
                Validate.notNull(f, "BinaryPredicate must not be null"),
                Validate.notNull(g, "left Function must not be null"),
                Validate.notNull(h, "right Function must not be null")
        );
    }

    // function interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public boolean test(L left, R right) {
        return helper.test(left, right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CompositeBinaryPredicate<?, ?>)) {
            return false;
        }
        CompositeBinaryPredicate<?, ?> that = (CompositeBinaryPredicate<?, ?>) obj;
        return this.helper.f.equals(that.helper.f)
                && this.helper.g.equals(that.helper.g)
                && this.helper.h.equals(that.helper.h);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "CompositeBinaryPredicate".hashCode();
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
        return "CompositeBinaryPredicate<" + helper.f + ";" + helper.g + ";" + helper.h + ">";
    }

}
