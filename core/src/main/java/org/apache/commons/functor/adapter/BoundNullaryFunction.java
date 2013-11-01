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
 * {@link Function Function}
 * to the
 * {@link NullaryFunction NullaryFunction} interface
 * using a constant unary argument.
 *
 * @param <T> the returned value type.
 * @version $Revision: 1345136 $ $Date: 2012-06-01 09:47:06 -0300 (Fri, 01 Jun 2012) $
 */
public final class BoundNullaryFunction<T> implements NullaryFunction<T> {
    /** The {@link Function Function} I'm wrapping. */
    private final Function<Object, ? extends T> function;
    /** The argument to pass to {@code function}. */
    private final Object arg;

    /**
     * Create a new BoundNullaryFunction instance.
     * @param <A> the argument value type
     * @param function the function to adapt
     * @param arg the constant argument to use
     */
    @SuppressWarnings("unchecked")
    public <A> BoundNullaryFunction(Function<? super A, ? extends T> function, A arg) {
        this.function =
            (Function<Object, ? extends T>) Validate.notNull(function,
                "Function argument was null");
        this.arg = arg;
    }

    /**
     * {@inheritDoc}
     */
    public T evaluate() {
        return function.evaluate(arg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BoundNullaryFunction<?>)) {
            return false;
        }
        BoundNullaryFunction<?> that = (BoundNullaryFunction<?>) obj;
        if (!(that.function.equals(this.function))) {
            return false;
        }
        return that.arg == this.arg || that.arg != null && that.arg.equals(this.arg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = "BoundNullaryFunction".hashCode();
        result <<= 2;
        result |= function.hashCode();
        result <<= 2;
        return arg == null ? result : result | arg.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BoundNullaryFunction<" + function.toString() + "(" + arg + ")>";
    }

    /**
     * Adapt the given, possibly-<code>null</code>,
     * {@link Function Function} to the
     * {@link NullaryFunction NullaryFunction} interface by binding
     * the specified <code>Object</code> as a constant
     * argument.
     * When the given <code>Function</code> is <code>null</code>,
     * returns <code>null</code>.
     * @param <A> input type
     * @param <T> result type
     * @param function the possibly-<code>null</code>
     *        {@link Function Function} to adapt
     * @param arg the object to bind as a constant argument
     * @return a <code>BoundNullaryFunction</code> wrapping the given
     *         {@link Function Function}, or <code>null</code>
     *         if the given <code>Function</code> is <code>null</code>
     */
    public static <A, T> BoundNullaryFunction<T> bind(Function<? super A, ? extends T> function, A arg) {
        return null == function ? null : new BoundNullaryFunction<T>(function, arg);
    }

}
