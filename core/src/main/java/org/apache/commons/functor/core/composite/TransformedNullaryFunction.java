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

import org.apache.commons.functor.NullaryFunction;
import org.apache.commons.functor.Function;
import org.apache.commons.lang3.Validate;

/**
 * A NullaryFunction whose result is then run through a Function.
 *
 * @param <T> the returned value type.
 * @version $Revision: 1365329 $ $Date: 2012-07-24 19:34:23 -0300 (Tue, 24 Jul 2012) $
 */
public class TransformedNullaryFunction<T> implements NullaryFunction<T> {

    /**
     * Type-remembering helper.
     *
     * @param <X> the adapted function argument type
     */
    private static final class Helper<X, T> implements NullaryFunction<T> {
        /**
         * The preceding function.
         */
        private NullaryFunction<? extends X> preceding;
        /**
         * The following function.
         */
        private Function<? super X, ? extends T> following;

        /**
         * Create a new Helper.
         * @param preceding NullaryFunction
         * @param following Function
         */
        private Helper(NullaryFunction<? extends X> preceding, Function<? super X, ? extends T> following) {
            this.preceding = Validate.notNull(preceding, "NullaryFunction argument was null");
            this.following = Validate.notNull(following, "Function argument was null");
        }

        /**
         * {@inheritDoc}
         */
        public T evaluate() {
            return following.evaluate(preceding.evaluate());
        }
    }

    /**
     * The adapted helper.
     */
    private final Helper<?, T> helper;

    /**
     * Create a new TransformedNullaryFunction.
     * @param <X> the preceding function argument type.
     * @param preceding NullaryFunction
     * @param following Function
     */
    public <X> TransformedNullaryFunction(NullaryFunction<? extends X> preceding,
            Function<? super X, ? extends T> following) {
        this.helper = new Helper<X, T>(preceding, following);
    }

    /**
     * {@inheritDoc}
     */
    public final T evaluate() {
        return helper.evaluate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TransformedNullaryFunction<?>)) {
            return false;
        }
        TransformedNullaryFunction<?> that = (TransformedNullaryFunction<?>) obj;
        return this.helper.preceding.equals(that.helper.preceding)
                && this.helper.following.equals(that.helper.following);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = "TransformedNullaryFunction".hashCode();
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
        return "TransformedNullaryFunction<" + helper.preceding + "; " + helper.following + ">";
    }
}
