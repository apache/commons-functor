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
package org.apache.commons.functor.core.composite;

import org.apache.commons.functor.Function;
import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.adapter.PredicateFunction;
import org.apache.commons.lang3.Validate;

/**
 * A {@link Predicate Predicate}
 * representing the composition of
 * {@link Function Functions},
 * "chaining" the output of one to the input
 * of another.  For example,
 * <pre>new CompositePredicate(p).of(f)</pre>
 * {@link #test tests} to
 * <code>p.test(f.evaluate(obj))</code>, and
 * <pre>new CompositePredicate(p).of(f).of(g)</pre>
 * {@link #test tests} to
 * <code>p.test(f.evaluate(g.evaluate(obj)))</code>.
 * @param <A> the predicate argument type.
 * @version $Revision: 1345136 $ $Date: 2012-06-01 09:47:06 -0300 (Fri, 01 Jun 2012) $
 */
public final class CompositePredicate<A> implements Predicate<A> {
    // attributes
    // ------------------------------------------------------------------------
    /**
     * The adapted composite function.
     */
    private final CompositeFunction<? super A, Boolean> function;

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new CompositePredicate.
     * @param predicate Predicate against which the composite functions' output will be tested
     */
    public CompositePredicate(Predicate<? super A> predicate) {
        this.function =
            new CompositeFunction<A, Boolean>(
                new PredicateFunction<A>(Validate.notNull(predicate,
                    "predicate must not be null")));
    }

    /**
     * Create a new CompositePredicate.
     * @param function delegate
     */
    private CompositePredicate(CompositeFunction<? super A, Boolean> function) {
        this.function = function;
    }

    // modifiers
    // ------------------------------------------------------------------------
    /**
     * Fluently obtain a CompositePredicate that applies our predicate to the result of the preceding function.
     * @param <P> the input function left argument and output predicate argument types
     * @param preceding Function
     * @return CompositePredicate<P>
     */
    public <P> CompositePredicate<P> of(Function<? super P, ? extends A> preceding) {
        return new CompositePredicate<P>(function.of(preceding));
    }

    // predicate interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public boolean test(A obj) {
        return function.evaluate(obj).booleanValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CompositePredicate<?>)) {
            return false;
        }
        CompositePredicate<?> that = (CompositePredicate<?>) obj;
        return this.function.equals(that.function);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "CompositePredicate".hashCode();
        hash <<= 2;
        hash ^= function.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "CompositeFunction<" + function + ">";
    }

}
