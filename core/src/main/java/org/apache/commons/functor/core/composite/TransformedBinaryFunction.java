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
import org.apache.commons.functor.Function;
import org.apache.commons.lang3.Validate;

/**
 * A BinaryFunction whose result is then run through a Function.
 *
 * @param <L> the left argument type.
 * @param <R> the right argument type.
 * @param <T> the returned value type.
 * @version $Revision$ $Date$
 */
public class TransformedBinaryFunction<L, R, T> implements BinaryFunction<L, R, T> {
    /**
     * Type-remembering helper.
     *
     * @param <X> the following function left argument.
     */
    private static final class Helper<X, L, R, T> implements BinaryFunction<L, R, T> {
        /**
         * The preceding function.
         */
        private BinaryFunction<? super L, ? super R, ? extends X> preceding;
        /**
         * The following function.
         */
        private Function<? super X, ? extends T> following;

        /**
         * Create a new Helper.
         * @param preceding BinaryFunction
         * @param following Function
         */
        private Helper(BinaryFunction<? super L, ? super R, ? extends X> preceding,
                Function<? super X, ? extends T> following) {
            this.preceding = Validate.notNull(preceding, "BinaryFunction argument was null");
            this.following = Validate.notNull(following, "Function argument was null");
        }

        /**
         * {@inheritDoc}
         */
        public T evaluate(L left, R right) {
            return following.evaluate(preceding.evaluate(left, right));
        }
    }

    /**
     * The adapted helper.
     */
    private final Helper<?, L, R, T> helper;

    /**
     * Create a new TransformedBinaryFunction.
     * @param <X> the following function left argument.
     * @param preceding BinaryFunction
     * @param following Function
     */
    public <X> TransformedBinaryFunction(BinaryFunction<? super L, ? super R, ? extends X> preceding,
            Function<? super X, ? extends T> following) {
        this.helper = new Helper<X, L, R, T>(preceding, following);
    }

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
        if (!(obj instanceof TransformedBinaryFunction<?, ?, ?>)) {
            return false;
        }
        TransformedBinaryFunction<?, ?, ?> that = (TransformedBinaryFunction<?, ?, ?>) obj;
        return this.helper.preceding.equals(that.helper.preceding)
                && this.helper.following.equals(that.helper.following);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = "TransformedBinaryFunction".hashCode();
        result <<= 2;
        result |= helper.following.hashCode();
        result <<= 2;
        result |= helper.preceding.hashCode();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TransformedBinaryFunction<" + helper.preceding + "; " + helper.following + ">";
    }
}
