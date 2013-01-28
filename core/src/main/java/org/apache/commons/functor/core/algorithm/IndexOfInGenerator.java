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

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.UnaryProcedure;
import org.apache.commons.functor.generator.Generator;
import org.apache.commons.functor.generator.loop.PredicatedGenerator;
import org.apache.commons.functor.generator.loop.UntilGenerate;

/**
 * Return the index of the first Object in a {@link PredicatedGenerator} matching a
 * {@link UnaryPredicate}, or -1 if not found.
 *
 * @param <T> the procedure argument types
 * @version $Revision$ $Date$
 */
public final class IndexOfInGenerator<T>
    implements BinaryFunction<Generator<? extends T>, UnaryPredicate<? super T>, Number>, Serializable {
    /**
     * serialVersionUID declaration.
     */
    private static final long serialVersionUID = -2672603607256310480L;
    /**
     * A static {@code IndexOfInGenerator} instance reference.
     */
    private static final IndexOfInGenerator<Object> INSTANCE = new IndexOfInGenerator<Object>();

    /**
     * Helper procedure.
     *
     * @param <T> the procedure argument type
     */
    private static class IndexProcedure<T> implements UnaryProcedure<T> {
        /**
         * A local accumulator to increment the number of attempts.
         */
        private long current = 0L;

        /**
         * {@inheritDoc}
         */
        public void run(T obj) {
            current++;
        }
    }

    /**
     * {@inheritDoc}
     * @param left Generator
     * @param right UnaryPredicate
     */
    public Number evaluate(Generator<? extends T> left, UnaryPredicate<? super T> right) {
        final IndexProcedure<T> findProcedure = new IndexProcedure<T>();
        new UntilGenerate<T>(right, left).run(findProcedure);
        return Long.valueOf(findProcedure.current);
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
