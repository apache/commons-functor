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

import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.Predicate;
import org.apache.commons.lang3.Validate;

/**
 * Adapts a BinaryPredicate as a Predicate by sending the same argument to both sides of the BinaryPredicate.
 * @param <A> the argument type.
 * @version $Revision: 1345136 $ $Date: 2012-06-01 09:47:06 -0300 (Fri, 01 Jun 2012) $
 */
public final class BinaryPredicatePredicate<A> implements Predicate<A> {
    /**
     * The adapted {@link BinaryPredicate}.
     */
    private final BinaryPredicate<? super A, ? super A> predicate;

    /**
     * Create a new BinaryPredicatePredicate.
     * @param predicate to adapt
     */
    public BinaryPredicatePredicate(BinaryPredicate<? super A, ? super A> predicate) {
        this.predicate = Validate.notNull(predicate, "BinaryPredicate argument was null");
    }

    /**
     * {@inheritDoc}
     */
    public boolean test(A obj) {
        return predicate.test(obj, obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BinaryPredicatePredicate<?>)) {
            return false;
        }
        BinaryPredicatePredicate<?> that = (BinaryPredicatePredicate<?>) obj;
        return this.predicate.equals(that.predicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return ("BinaryPredicatePredicate".hashCode() << 2) | predicate.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BinaryPredicatePredicate<" + predicate + ">";
    }

    /**
     * Adapt a BinaryPredicate as a Predicate.
     * @param <A> the argument type.
     * @param predicate BinaryPredicate to adapt
     * @return Predicate<A>
     */
    public static <A> Predicate<A> adapt(BinaryPredicate<? super A, ? super A> predicate) {
        return null == predicate ? null : new BinaryPredicatePredicate<A>(predicate);
    }

}
