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

import java.util.ListIterator;

import org.apache.commons.functor.BinaryProcedure;
import org.apache.commons.functor.Function;

/**
 * Implements an in-place transformation of a ListIterator's contents.
 *
 * @param <T> the arguments type
 * @version $Revision$ $Date$
 */
public final class InPlaceTransform<T>
    implements BinaryProcedure<ListIterator<T>, Function<? super T, ? extends T>> {
    /**
     * A static {@code InPlaceTransform} instance reference.
     */
    private static final InPlaceTransform<Object> INSTANCE = new InPlaceTransform<Object>();

    /**
     * {@inheritDoc}
     * @param left {@link ListIterator}
     * @param right {@link Function}
     */
    public void run(ListIterator<T> left, Function<? super T, ? extends T> right) {
        while (left.hasNext()) {
            left.set(right.evaluate(left.next()));
        }
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
        return "InPlaceTransform";
    }

    /**
     * Get an {@link InPlaceTransform} instance.
     * @return InPlaceTransform
     */
    public static InPlaceTransform<Object> instance() {
        return INSTANCE;
    }

}
