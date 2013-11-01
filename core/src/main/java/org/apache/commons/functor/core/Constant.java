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
package org.apache.commons.functor.core;

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.Function;
import org.apache.commons.functor.NullaryFunction;
import org.apache.commons.functor.NullaryPredicate;
import org.apache.commons.functor.Predicate;

/**
 * {@link #evaluate Evaluates} to constant value.
 * <p>
 * {@link #test Tests} to a constant value, assuming
 * a boolean of Boolean value is supplied.
 * </p>
 * @param <T> the returned value type.
 * @version $Revision$ $Date$
 */
public final class Constant<T> implements NullaryFunction<T>, Function<Object, T>, BinaryFunction<Object, Object, T>,
        NullaryPredicate, Predicate<Object>, BinaryPredicate<Object, Object> {

    // static attributes
    // ------------------------------------------------------------------------
    /**
     * Constant for <code>true</code>.
     */
    public static final Constant<Boolean> TRUE = of(Boolean.TRUE);

    /**
     * Constant for <code>false</code>.
     */
    public static final Constant<Boolean> FALSE = of(Boolean.FALSE);

    // attributes
    // ------------------------------------------------------------------------
    /**
     * The constant value.
     */
    private final T value;

    // constructor
    // ------------------------------------------------------------------------

    /**
     * Create a new Constant.
     * @param value Object
     */
    public Constant(T value) {
        this.value = value;
    }

    // function interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public T evaluate() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    public T evaluate(Object obj) {
        return evaluate();
    }

    /**
     * {@inheritDoc}
     */
    public T evaluate(Object left, Object right) {
        return evaluate();
    }

    /**
     * {@inheritDoc}
     */
    public boolean test() {
        return ((Boolean) evaluate()).booleanValue();
    }

    /**
     * {@inheritDoc}
     */
    public boolean test(Object obj) {
        return test();
    }

    /**
     * {@inheritDoc}
     */
    public boolean test(Object left, Object right) {
        return test();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Constant<?>)) {
            return false;
        }
        Constant<?> that = (Constant<?>) obj;
        return null == this.value ? null == that.value : this.value.equals(that.value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "Constant".hashCode();
        if (null != value) {
            hash ^= value.hashCode();
        }
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Constant<" + String.valueOf(value) + ">";
    }

    // static methods
    // ------------------------------------------------------------------------

    /**
     * Get a <code>Constant</code> that always
     * returns <code>true</code>.
     * @return a <code>Constant</code> that always
     *         returns <code>true</code>
     */
    public static Constant<Boolean> truePredicate() {
        return TRUE;
    }

    /**
     * Get a <code>Constant</code> that always
     * returns <code>false</code>.
     * @return a <code>Constant</code> that always
     *         returns <code>false</code>
     */
    public static Constant<Boolean> falsePredicate() {
        return FALSE;
    }

    /**
     * Get a <code>Constant</code> that always
     * returns <i>value</i>.
     * @param value the constant value
     * @return a <code>Constant</code> that always
     *         returns <i>value</i>
     */
    public static Constant<Boolean> predicate(boolean value) {
        return value ? TRUE : FALSE;
    }

    /**
     * Get a Constant instance for the specified value.
     * @param <T> the constant value
     * @param value T
     * @return Constant<T>
     */
    public static <T> Constant<T> of(T value) {
        return new Constant<T>(value);
    }

}
