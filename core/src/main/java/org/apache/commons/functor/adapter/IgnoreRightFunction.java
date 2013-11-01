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
 * by ignoring the second binary argument.
 *
 * @param <L> the left argument type.
 * @param <R> the right argument type.
 * @param <T> the returned value type.
 * @version $Revision$ $Date$
 */
public final class IgnoreRightFunction<L, R, T> implements BinaryFunction<L, R, T> {
    /** The {@link Function Function} I'm wrapping. */
    private final Function<? super L, ? extends T> function;

    /**
     * Create a new IgnoreRightFunction.
     * @param function Function to wrap
     */
    public IgnoreRightFunction(Function<? super L, ? extends T> function) {
        this.function = Validate.notNull(function, "Function argument was null");
    }

    /**
     * {@inheritDoc}
     */
    public T evaluate(L left, R right) {
        return function.evaluate(left);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof IgnoreRightFunction<?, ?, ?>)) {
            return false;
        }
        IgnoreRightFunction<?, ?, ?> that = (IgnoreRightFunction<?, ?, ?>) obj;
        return this.function.equals(that.function);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "IgnoreRightFunction".hashCode();
        hash ^= function.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "IgnoreRightFunction<" + function + ">";
    }

    /**
     * Adapt a Function to the BinaryFunction interface.
     * @param <L> left type
     * @param <R> right type
     * @param <T> result type
     * @param function Function to adapt
     * @return IgnoreRightFunction
     */
    public static <L, R, T> IgnoreRightFunction<L, R, T> adapt(Function<? super L, ? extends T> function) {
        return null == function ? null : new IgnoreRightFunction<L, R, T>(function);
    }

}
