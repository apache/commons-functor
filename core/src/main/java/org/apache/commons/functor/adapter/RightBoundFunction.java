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
package org.apache.commons.functor.adapter;

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.Function;
import org.apache.commons.lang3.Validate;

/**
 * Adapts a
 * {@link BinaryFunction BinaryFunction}
 * to the
 * {@link Function Function} interface
 * using a constant right-side argument.
 *
 * @param <A> the argument type.
 * @param <T> the returned value type.
 * @version $Revision$ $Date$
 */
public final class RightBoundFunction<A, T> implements Function<A, T> {
    /** The {@link BinaryFunction BinaryFunction} I'm wrapping. */
    private final BinaryFunction<? super A, Object, ? extends T> function;
    /** The parameter to pass to {@code function}. */
    private final Object param;

    /**
     * @param <R> bound arg type
     * @param function the function to adapt
     * @param arg the constant argument to use
     */
    @SuppressWarnings("unchecked")
    public <R> RightBoundFunction(BinaryFunction<? super A, ? super R, ? extends T> function, R arg) {
        this.function =
            (BinaryFunction<? super A, Object, ? extends T>) Validate.notNull(
                function, "left-hand BinaryFunction argument was null");
        this.param = arg;
    }

    /**
     * {@inheritDoc}
     */
    public T evaluate(A obj) {
        return function.evaluate(obj, param);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RightBoundFunction<?, ?>)) {
            return false;
        }
        RightBoundFunction<?, ?> that = (RightBoundFunction<?, ?>) obj;
        return function.equals(that.function)
                && (null == param ? null == that.param : param.equals(that.param));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "RightBoundFunction".hashCode();
        hash <<= 2;
        hash ^= function.hashCode();
        if (null != param) {
            hash <<= 2;
            hash ^= param.hashCode();
        }
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "RightBoundFunction<" + function + "(?," + param + ")>";
    }

    /**
     * Adapt a BinaryFunction to the Function interface.
     * @param <L> the left argument type.
     * @param <R> the right argument type.
     * @param <T> the returned value type.
     * @param function BinaryFunction to adapt
     * @param arg Object that will always be used for the right side of the BinaryFunction delegate.
     * @return RightBoundFunction
     */
    public static <L, R, T> RightBoundFunction<L, T> bind(BinaryFunction<? super L, ? super R, ? extends T> function,
                                                          R arg) {
        return null == function ? null : new RightBoundFunction<L, T>(function, arg);
    }

}
