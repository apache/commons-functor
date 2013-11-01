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

import org.apache.commons.functor.NullaryFunction;
import org.apache.commons.functor.Function;
import org.apache.commons.lang3.Validate;

/**
 * Adapts a
 * {@link NullaryFunction NullaryFunction}
 * to the
 * {@link Function Function} interface
 * by ignoring the unary argument.
 *
 * @param <A> the argument type.
 * @param <T> the returned value type.
 * @version $Revision: 1365377 $ $Date: 2012-07-24 21:59:23 -0300 (Tue, 24 Jul 2012) $
 */
public final class NullaryFunctionFunction<A, T> implements Function<A, T> {
    /** The {@link NullaryFunction NullaryFunction} I'm wrapping. */
    private final NullaryFunction<? extends T> function;

    /**
     * Create a new NullaryFunctionFunction.
     * @param function to adapt
     */
    public NullaryFunctionFunction(NullaryFunction<? extends T> function) {
        this.function = Validate.notNull(function, "NullaryFunction argument was null");
    }

    /**
     * {@inheritDoc}
     */
    public T evaluate(A obj) {
        return function.evaluate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof NullaryFunctionFunction<?, ?>)) {
            return false;
        }
        NullaryFunctionFunction<?, ?> that = (NullaryFunctionFunction<?, ?>) obj;
        return this.function.equals(that.function);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "NullaryFunctionFunction".hashCode();
        hash ^= function.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "NullaryFunctionFunction<" + function + ">";
    }

    /**
     * Adapt a NullaryFunction to the Function interface.
     * @param <A> arg type
     * @param <T> result type
     * @param function to adapt
     * @return NullaryFunctionFunction
     */
    public static <A, T> NullaryFunctionFunction<A, T> adapt(NullaryFunction<? extends T> function) {
        return null == function ? null : new NullaryFunctionFunction<A, T>(function);
    }

}
