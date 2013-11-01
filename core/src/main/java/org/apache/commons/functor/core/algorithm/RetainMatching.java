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
package org.apache.commons.functor.core.algorithm;

import java.util.Iterator;

import org.apache.commons.functor.BinaryProcedure;
import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.core.composite.Not;

/**
 * Retain elements in left Iterator that match right Predicate.
 *
 * @param <T> the procedure argument type
 * @version $Revision$ $Date$
 */
public final class RetainMatching<T>
    implements BinaryProcedure<Iterator<? extends T>, Predicate<? super T>> {
    /**
     * A static {@code RetainMatching} instance reference.
     */
    private static final RetainMatching<Object> INSTANCE = new RetainMatching<Object>();
    /**
     * The {@code RemoveMatching} instance used to remove elements from input iterator.
     */
    private final RemoveMatching<T> removeMatching = new RemoveMatching<T>();

    /**
     * {@inheritDoc}
     * @param left {@link Iterator}
     * @param right {@link Predicate}
     */
    public void run(Iterator<? extends T> left, Predicate<? super T> right) {
        removeMatching.run(left, Not.not(right));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return obj == this || obj != null && obj.getClass().equals(getClass());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return System.identityHashCode(INSTANCE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "RetainMatching";
    }

    /**
     * Get a static {@link RetainMatching} instance.
     * @return {@link RetainMatching}
     */
    public static RetainMatching<Object> instance() {
        return INSTANCE;
    }
}
