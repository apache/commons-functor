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

import java.io.Serializable;

import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.adapter.RightBoundPredicate;

/**
 * {@link #test Tests}
 * <code>true</code> iff its argument
 * {@link Class#isInstance is an instance}
 * of some specified {@link Class Class}.
 *
 * @param <T> the object instance has to be tested against the input class.
 * @version $Revision$ $Date$
 * @author Rodney Waldhoff
 */
public final class IsInstance<T> implements BinaryPredicate<T, Class<?>>, Serializable {
    /**
     * Basic IsInstanceOf instance.
     */
    public static final IsInstance<Object> INSTANCE = IsInstance.<Object>instance();
    /**
     * serialVersionUID declaration.
     */
    private static final long serialVersionUID = 9104265415387129627L;
    /**
     * The non zero number used to shift the initial hashcode.
     */
    private static final int NONZERO_SHIFT_NUMBER = 4;
    /**
     * The non zero number used to bitwise or the hashcode.
     */
    private static final int NONZERO_BITWISE_NUMBER = 37;

    // predicate interface
    // ------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public boolean test(T left, Class<?> right) {
        return right.isInstance(left);
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object that) {
        return that instanceof IsInstance<?>;
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return ("IsInstance".hashCode() << NONZERO_SHIFT_NUMBER) | NONZERO_BITWISE_NUMBER;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return "IsInstance";
    }

    /**
     * Get an IsInstance instance.
     * @param <T> the object instance has to be tested against the input class.
     * @return IsInstance<T>
     */
    public static <T> IsInstance<T> instance() {
        return new IsInstance<T>();
    }

    /**
     * Get an IsInstanceOf UnaryPredicate.
     * @param <T> the object instance has to be tested against the input class.
     * @param clazz bound right-side argument
     * @return UnaryPredicate<T>
     */
    public static <T> UnaryPredicate<T> of(Class<?> clazz) {
        return RightBoundPredicate.bind(new IsInstance<T>(), clazz);
    }
}
