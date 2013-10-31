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
 * Transposes (swaps) the arguments to some other
 * {@link BinaryFunction function}.
 * For example, given a function <i>f</i>
 * and the ordered pair of arguments <i>a</i>,
 * <i>b</i>.
 * {@link #evaluate evaluates} to
 * <code>f.evaluate(b,a)</code>.
 *
 * @param <L> the left argument type.
 * @param <R> the right argument type.
 * @param <T> the returned value type.
 * @version $Revision$ $Date$
 */
public class TransposedFunction<L, R, T> implements BinaryFunction<L, R, T> {
    // attributes
    // ------------------------------------------------------------------------
    /**
     * The adapted function.
     */
    private final BinaryFunction<? super R, ? super L, ? extends T> function;

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new TransposedFunction.
     * @param function BinaryFunction to transpose.
     */
    public TransposedFunction(BinaryFunction<? super R, ? super L, ? extends T> function) {
        this.function = Validate.notNull(function, "BinaryFunction argument was null");
    }

    // functor interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public final T evaluate(L left, R right) {
        return function.evaluate(right, left);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TransposedFunction<?, ?, ?>)) {
            return false;
        }
        TransposedFunction<?, ?, ?> that = (TransposedFunction<?, ?, ?>) obj;
        return this.function.equals(that.function);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "TransposedFunction".hashCode();
        hash ^= function.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TransposedFunction<" + function + ">";
    }

    // static
    // ------------------------------------------------------------------------
    /**
     * Transpose a BinaryFunction.
     * @param <L> the left argument type.
     * @param <R> the right argument type.
     * @param <T> the returned value type.
     * @param f BinaryFunction to transpose
     * @return TransposedFunction
     */
    public static <L, R, T> TransposedFunction<R, L, T> transpose(BinaryFunction<? super L, ? super R, ? extends T> f) {
        return null == f ? null : new TransposedFunction<R, L, T>(f);
    }

}
