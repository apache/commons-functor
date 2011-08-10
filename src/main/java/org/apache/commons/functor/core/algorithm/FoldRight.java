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
 * @version $Revision$ $Date$
 */
public class FoldRight<T> implements UnaryFunction<Generator<T>, T>, BinaryFunction<Generator<T>, T, T>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4671387086700391506L;

    /**
     * Helper class
     */
    private static class FoldRightHelper<T> implements UnaryProcedure<T> {
        private final Stack<T> stk = new Stack<T>();
        private final BinaryFunction<? super T, ? super T, ? extends T> function;
        private final T seed;
        private final boolean hasSeed;

        /**
         * Create a seedless FoldRightHelper.
         */
        public FoldRightHelper(BinaryFunction<? super T, ? super T, ? extends T> function) {
            this(null, function);
        }

        /**
         * Create a new FoldRightHelper.
         * @param seed initial left argument
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
    public T evaluate(Generator<T> obj) {
        FoldRightHelper<T> helper = new FoldRightHelper<T>(function);
        obj.run(helper);
        return helper.getResult();
    }

    /**
     * {@inheritDoc}
     * @param left {@link Generator} to transform
     * @param right seed object
     */
    public T evaluate(Generator<T> left, T right) {
        FoldRightHelper<T> helper = new FoldRightHelper<T>(right, function);
        left.run(helper);
        return helper.getResult();
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof FoldRight<?> == false) {
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
