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
 * {@link Function Function}
 * to the
 * {@link BinaryFunction BinaryFunction} interface
 * by ignoring the first binary argument.
 *
 * @param <L> the left argument type.
 * @param <R> the right argument type.
 * @param <T> the returned value type.
 * @version $Revision$ $Date$
 */
public final class IgnoreLeftFunction<L, R, T> implements BinaryFunction<L, R, T> {
    /** The {@link Function Function} I'm wrapping. */
    private final Function<? super R, ? extends T> function;

    /**
     * Create a new IgnoreLeftFunction.
     * @param function Function for right argument
     */
    public IgnoreLeftFunction(Function<? super R, ? extends T> function) {
        this.function = Validate.notNull(function, "Function argument was null");
    }

    /**
     * {@inheritDoc}
     */
    public T evaluate(L left, R right) {
        return function.evaluate(right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof IgnoreLeftFunction<?, ?, ?>)) {
            return false;
        }
        IgnoreLeftFunction<?, ?, ?> that = (IgnoreLeftFunction<?, ?, ?>) obj;
        return this.function.equals(that.function);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "IgnoreLeftFunction".hashCode();
        hash ^= function.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "IgnoreLeftFunction<" + function + ">";
    }

    /**
     * Adapt a Function to the BinaryFunction interface.
     * @param <L> left type
     * @param <R> right type
     * @param <T> result type
     * @param function to adapt
     * @return IgnoreLeftFunction
     */
    public static <L, R, T> IgnoreLeftFunction<L, R, T> adapt(Function<? super R, ? extends T> function) {
        return null == function ? null : new IgnoreLeftFunction<L, R, T>(function);
    }

}
