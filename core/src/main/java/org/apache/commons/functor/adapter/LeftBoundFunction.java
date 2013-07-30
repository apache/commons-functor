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

import java.io.Serializable;

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.Function;
import org.apache.commons.lang3.Validate;

/**
 * Adapts a
 * {@link BinaryFunction BinaryFunction}
 * to the
 * {@link Function Function} interface
 * using a constant left-side argument.
 * <p/>
 * Note that although this class implements
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if the
 * underlying objects are.  Attempts to serialize
 * an instance whose delegates are not
 * <code>Serializable</code> will result in an exception.
 *
 * @param <A> the argument type.
 * @param <T> the returned value type.
 * @version $Revision$ $Date$
 */
public final class LeftBoundFunction<A, T> implements Function<A, T>, Serializable {
    /**
     * serialVersionUID declaration.
     */
    private static final long serialVersionUID = -7456827102718911769L;
    /** The {@link BinaryFunction BinaryFunction} I'm wrapping. */
    private final BinaryFunction<Object, ? super A, ? extends T> function;
    /** The parameter to pass to {@code function}. */
    private final Object param;

    /**
     * Create a new LeftBoundFunction instance.
     * @param <L> bound arg type
     * @param function the function to adapt
     * @param arg the constant argument to use
     */
    @SuppressWarnings("unchecked")
    public <L> LeftBoundFunction(BinaryFunction<? super L, ? super A, ? extends T> function, L arg) {
        this.function =
            (BinaryFunction<Object, ? super A, ? extends T>) Validate.notNull(
                function, "BinaryFunction argument was null");
        this.param = arg;
    }

    /**
     * {@inheritDoc}
     */
    public T evaluate(A obj) {
        return function.evaluate(param, obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object that) {
        return that == this || (that instanceof LeftBoundFunction<?, ?> && equals((LeftBoundFunction<?, ?>) that));
    }

    /**
     * Learn whether another LeftBoundFunction is equal to this.
     * @param that LeftBoundFunction to test
     * @return boolean
     */
    public boolean equals(LeftBoundFunction<?, ?> that) {
        return null != that
                && function.equals(that.function)
                && (null == param ? null == that.param : param.equals(that.param));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "LeftBoundFunction".hashCode();
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
        return "LeftBoundFunction<" + function + "(" + param + ",?)>";
    }

    /**
     * Adapt a BinaryFunction as a Function.
     * @param <L> left type
     * @param <R> right type
     * @param <T> result type
     * @param function to adapt
     * @param arg left side argument
     * @return LeftBoundFunction
     */
    public static <L, R, T> LeftBoundFunction<R, T> bind(
            BinaryFunction<? super L, ? super R, ? extends T> function, L arg) {
        return null == function ? null : new LeftBoundFunction<R, T>(function, arg);
    }

}
