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

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.Function;
import org.apache.commons.functor.Procedure;
import org.apache.commons.functor.generator.Generator;

/**
 * Functional left-fold algorithm against the elements of a {@link Generator}.
 * Uses the seed object (if supplied) as the initial left-side argument to the {@link BinaryFunction},
 * then uses the result of that evaluation as the next left-side argument, until the {@link Generator}'s
 * elements have been expended.
 *
 * @param <T> the returned evaluation type.
 * @version $Revision$ $Date$
 */
public class FoldLeft<T> implements Function<Generator<T>, T>, BinaryFunction<Generator<T>, T, T> {

    /**
     * Helper procedure.
     *
     * @param <T> the returned evaluation type.
     */
    private static class FoldLeftHelper<T> implements Procedure<T> {
        /**
         * The wrapped function.
         */
        private final BinaryFunction<? super T, ? super T, ? extends T> function;
        /**
         * The seed object.
         */
        private T seed;
        /**
         * Flag to check the helper started or not.
         */
        private boolean started;

        /**
         * Create a seedless FoldLeftHelper.
         *
         * @param function The wrapped function
         */
        public FoldLeftHelper(BinaryFunction<? super T, ? super T, ? extends T> function) {
            this(null, function);
        }

        /**
         * Create a new FoldLeftHelper.
         *
         * @param seed initial left-side argument
         * @param function The wrapped function
         */
        FoldLeftHelper(T seed, BinaryFunction<? super T, ? super T, ? extends T> function) {
            this.seed = seed;
            started = seed != null ? true : false;
            this.function = function;
        }

        /**
         * {@inheritDoc}
         */
        public void run(T obj) {
            if (!started) {
                seed = obj;
                started = true;
            } else {
                seed = function.evaluate(seed, obj);
            }
        }

        /**
         * Get current result.
         * @return Object
         */
        T getResult() {
            return started ? seed : null;
        }

    }

    /**
     * {@link BinaryFunction} to apply to each (seed, next).
     */
    private final BinaryFunction<? super T, ? super T, ? extends T> function;

    /**
     * Create a new FoldLeft.
     * @param func {@link BinaryFunction} to apply to each (seed, next)
     */
    public FoldLeft(BinaryFunction<? super T, ? super T, ? extends T> func) {
        this.function = func;
    }

    /**
     * {@inheritDoc}
     * @param obj {@link Generator} to transform
     */
    public final T evaluate(Generator<T> obj) {
        FoldLeftHelper<T> helper = new FoldLeftHelper<T>(function);
        obj.run(helper);
        return helper.getResult();
    }

    /**
     * {@inheritDoc}
     * @param left {@link Generator} to transform
     * @param right initial left-side seed object
     */
    public final T evaluate(Generator<T> left, T right) {
        FoldLeftHelper<T> helper = new FoldLeftHelper<T>(right, function);
        left.run(helper);
        return helper.getResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FoldLeft<?>)) {
            return false;
        }
        return ((FoldLeft<?>) obj).function.equals(function);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return "FoldLeft".hashCode() << 2 ^ function.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FoldLeft<" + function + ">";
    }

}
