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

import java.io.Serializable;
import java.util.Stack;

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.UnaryFunction;
import org.apache.commons.functor.UnaryProcedure;
import org.apache.commons.functor.generator.Generator;

/**
 * Functional right-fold algorithm against the elements of a {@link Generator}.
 * Uses the seed object (if supplied) as the initial right-side argument to the {@link BinaryFunction},
 * then uses the result of that evaluation as the next right-side argument, until the {@link Generator}'s
 * elements have been expended.
 *
 * @param <T> the returned evaluation type.
 * @version $Revision$ $Date$
 */
public class FoldRight<T> implements UnaryFunction<Generator<T>, T>, BinaryFunction<Generator<T>, T, T>, Serializable {

    /**
     * serialVersionUID declaration.
     */
    private static final long serialVersionUID = 4671387086700391506L;

    /**
     * Helper class.
     *
     * @param <T> the returned evaluation type.
     */
    private static class FoldRightHelper<T> implements UnaryProcedure<T> {
        /**
         * The stack where storing the wrapped function evaluations.
         */
        private final Stack<T> stk = new Stack<T>();
        /**
         * The wrapped function.
         */
        private final BinaryFunction<? super T, ? super T, ? extends T> function;
        /**
         * The seed object.
         */
        private final T seed;
        /**
         * Flag to check the helper started or not.
         */
        private final boolean hasSeed;

        /**
         * Create a seedless FoldRightHelper.
         *
         * @param function The wrapped function
         */
        public FoldRightHelper(BinaryFunction<? super T, ? super T, ? extends T> function) {
            this(null, function);
        }

        /**
         * Create a new FoldRightHelper.
         *
         * @param seed initial right-side argument
         * @param function The wrapped function
         */
        FoldRightHelper(T seed, BinaryFunction<? super T, ? super T, ? extends T> function) {
            this.seed = seed;
            hasSeed = seed != null ? true : false;
            this.function = function;
        }

        /**
         * {@inheritDoc}
         */
        public void run(T obj) {
            stk.push(obj);
        }

        /**
         * Get result after processing.
         * Get current result.
         * @return Object
         */
        T getResult() {
            T right = seed;
            if (!hasSeed) {
                if (stk.isEmpty()) {
                    return null;
                }
                right = stk.pop();
            }
            while (!stk.isEmpty()) {
                right = function.evaluate(stk.pop(), right);
            }
            return right;
        }

    }

    /**
     * {@link BinaryFunction} to apply to each (seed, next).
     */
    private final BinaryFunction<? super T, ? super T, ? extends T> function;

    /**
     * Create a new FoldRight.
     * @param function {@link BinaryFunction} to apply to each (seed, next)
     */
    public FoldRight(BinaryFunction<? super T, ? super T, ? extends T> function) {
        this.function = function;
    }

    /**
     * {@inheritDoc}
     * @param obj {@link Generator} to transform
     */
    public final T evaluate(Generator<T> obj) {
        FoldRightHelper<T> helper = new FoldRightHelper<T>(function);
        obj.run(helper);
        return helper.getResult();
    }

    /**
     * {@inheritDoc}
     * @param left {@link Generator} to transform
     * @param right initial right-side seed object
     */
    public final T evaluate(Generator<T> left, T right) {
        FoldRightHelper<T> helper = new FoldRightHelper<T>(right, function);
        left.run(helper);
        return helper.getResult();
    }

    /**
     * {@inheritDoc}
     */
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FoldRight<?>)) {
            return false;
        }
        return ((FoldRight<?>) obj).function.equals(function);
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return "FoldRight".hashCode() << 2 ^ function.hashCode();
    }

}
