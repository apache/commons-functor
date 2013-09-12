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
package org.apache.commons.functor.generator;

import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.Procedure;
import org.apache.commons.functor.core.composite.ConditionalProcedure;
import org.apache.commons.lang3.Validate;

/**
 * Generator that filters another Generator by only passing through those elements
 * that are matched by a specified Predicate.
 *
 * @param <E> the type of elements held in this generator.
 * @version $Revision$ $Date$
 */
public class FilteredGenerator<E> extends BaseGenerator<E> {

    /**
     * A generator can wrap another generator.
     * */
    private Generator<? extends E> wrappedGenerator;

    /**
     * The predicate used to filter.
     */
    private final Predicate<? super E> pred;

    /**
     * Create a new FilteredGenerator.
     * @param wrapped Generator to wrap
     * @param pred filtering Predicate
     */
    public FilteredGenerator(Generator<? extends E> wrapped, Predicate<? super E> pred) {
        this.wrappedGenerator = Validate.notNull(wrapped, "Generator argument was null");
        this.pred = Validate.notNull(pred, "Predicate argument was null");
    }

    /**
     * {@inheritDoc}
     */
    public void run(Procedure<? super E> proc) {
        getWrappedGenerator().run(new ConditionalProcedure<E>(pred, proc));
    }

    /**
     * Get the generator that is being wrapped.
     * @return Generator
     */
    protected Generator<? extends E> getWrappedGenerator() {
        return this.wrappedGenerator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FilteredGenerator<?>)) {
            return false;
        }
        FilteredGenerator<?> other = (FilteredGenerator<?>) obj;
        return other.getWrappedGenerator().equals(getWrappedGenerator()) && other.pred.equals(pred);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = "FilteredGenerator".hashCode();
        result <<= 2;
        Generator<?> gen = getWrappedGenerator();
        result ^= gen.hashCode();
        result <<= 2;
        result ^= pred.hashCode();
        return result;
    }
}
