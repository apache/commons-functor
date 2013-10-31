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

import org.apache.commons.functor.Function;
import org.apache.commons.lang3.Validate;

/**
 * A {@link Function Function}
 * representing the composition of
 * {@link Function Functions},
 * "chaining" the output of one to the input
 * of another.  For example,
 * <pre>new CompositeFunction(f).of(g)</pre>
 * {@link #evaluate evaluates} to
 * <code>f.evaluate(g.evaluate(obj))</code>, and
 * <pre>new CompositeFunction(f).of(g).of(h)</pre>
 * {@link #evaluate evaluates} to
 * <code>f.evaluate(g.evaluate(h.evaluate(obj)))</code>.
 * <p>
 * When the collection is empty, this function is
 * an identity function.
 * </p>
 * @param <A> the argument type.
 * @param <T> the returned value type.
 * @version $Revision: 1365329 $ $Date: 2012-07-24 19:34:23 -0300 (Tue, 24 Jul 2012) $
 */
public class CompositeFunction<A, T> implements Function<A, T> {

    /** Base hash integer used to shift hash. */
    private static final int HASH_SHIFT = 4;

    /**
     * Encapsulates a double function evaluation.
     * @param <A> argument type
     * @param <X> intermediate type
     * @param <T> return type
     */
    private static class Helper<X, A, T> implements Function<A, T> {
        /**
         * The last evaluator function.
         */
        private Function<? super X, ? extends T> following;
        /**
         * The first evaluator function.
         */
        private Function<? super A, ? extends X> preceding;

        /**
         * Create a new Helper.
         * @param following Function<X, Y>
         * @param preceding Function<Y, Z>
         */
        public Helper(Function<? super X, ? extends T> following,
                Function<? super A, ? extends X> preceding) {
            this.following = Validate.notNull(following, "Function argument must not be null");
            this.preceding = Validate.notNull(preceding, "Function argument must not be null");
        }

        /**
         * {@inheritDoc}
         */
        public T evaluate(A obj) {
            return following.evaluate(preceding.evaluate(obj));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            return obj == this || obj instanceof Helper<?, ?, ?> && equals((Helper<?, ?, ?>) obj);
        }

        /**
         * Checks if input helper is equals to this instance.
         *
         * @param helper the helper to check
         * @return true, if helpers are equals, false otherwise
         */
        private boolean equals(Helper<?, ?, ?> helper) {
            return helper.following.equals(following) && helper.preceding.equals(preceding);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            int result = "CompositeFunction$Helper".hashCode();
            result <<= 2;
            result ^= following.hashCode();
            result <<= 2;
            result ^= preceding.hashCode();
            return result;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return following.toString() + " of " + preceding.toString();
        }
    }

    /**
     * The adapted function.
     */
    private final Function<? super A, ? extends T> function;

    /**
     * Create a new CompositeFunction.
     * @param function Function to call
     */
    public CompositeFunction(Function<? super A, ? extends T> function) {
        this.function = Validate.notNull(function, "function must not be null");
    }

    /**
     * Creates a new {@link CompositeFunction} instance given the input functions.
     *
     * @param <X> the argument type.
     * @param following The first evaluator function.
     * @param preceding The last evaluator function.
     */
    private <X> CompositeFunction(Function<? super X, ? extends T> following,
            Function<? super A, ? extends X> preceding) {
        this.function = new Helper<X, A, T>(following, preceding);
    }

    /**
     * {@inheritDoc}
     */
    public final T evaluate(A obj) {
        return function.evaluate(obj);
    }

    /**
     * Fluently obtain a CompositeFunction that is "this function" applied to the specified preceding function.
     * @param <P> argument type of the resulting function.
     * @param preceding Function
     * @return CompositeFunction<P, T>
     */
    public final <P> CompositeFunction<P, T> of(Function<? super P, ? extends A> preceding) {
        Validate.notNull(preceding, "preceding function was null");
        return new CompositeFunction<P, T>(function, preceding);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CompositeFunction<?, ?>)) {
            return false;
        }
        CompositeFunction<?, ?> that = (CompositeFunction<?, ?>) obj;
        return this.function.equals(that.function);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        // by construction, list is never null
        return ("CompositeFunction".hashCode() << HASH_SHIFT) ^ function.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "CompositeFunction<" + function + ">";
    }

}
