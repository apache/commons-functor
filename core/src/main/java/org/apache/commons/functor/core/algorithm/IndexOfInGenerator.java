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
import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.Procedure;
import org.apache.commons.functor.generator.loop.LoopGenerator;

/**
 * Return the index of the first Object in a {@link LoopGenerator} matching a
 * {@link Predicate}, or -1 if not found.
 *
 * @param <T> the procedure argument types
 * @version $Revision$ $Date$
 */
public final class IndexOfInGenerator<T>
    implements BinaryFunction<LoopGenerator<? extends T>, Predicate<? super T>, Number> {
    /**
     * A static {@code IndexOfInGenerator} instance reference.
     */
    private static final IndexOfInGenerator<Object> INSTANCE = new IndexOfInGenerator<Object>();

    /**
     * Helper procedure.
     *
     * @param <T> the procedure argument type
     */
    private static class IndexProcedure<T> implements Procedure<T> {
        /**
         * The wrapped generator.
         */
        private final LoopGenerator<? extends T> generator;
        /**
         * The wrapped predicate.
         */
        private final Predicate<? super T> pred;
        /**
         * The number of iterations needed before the wrapped predicate found the target,
         * {@code -1} means the target was not found.
         */
        private long index = -1L;
        /**
         * A local accumulator to increment the number of attempts.
         */
        private long current = 0L;

        /**
         * Create a new IndexProcedure.
         *
         * @param generator The wrapped generator
         * @param pred The wrapped predicate
         */
        IndexProcedure(LoopGenerator<? extends T> generator, Predicate<? super T> pred) {
            this.generator = generator;
            this.pred = pred;
        }

        /**
         * {@inheritDoc}
         */
        public void run(T obj) {
            if (index < 0 && pred.test(obj)) {
                index = current;
                generator.stop();
            }
            current++;
        }
    }

    /**
     * {@inheritDoc}
     * @param left LoopGenerator
     * @param right Predicate
     */
    public Number evaluate(LoopGenerator<? extends T> left, Predicate<? super T> right) {
        IndexProcedure<T> findProcedure = new IndexProcedure<T>(left, right);
        left.run(findProcedure);
        return Long.valueOf(findProcedure.index);
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
        return "IndexOfInGenerator";
    }

    /**
     * Get a static {@link IndexOfInGenerator} instance.
     * @return {@link IndexOfInGenerator}
     */
    public static IndexOfInGenerator<Object> instance() {
        return INSTANCE;
    }
}
