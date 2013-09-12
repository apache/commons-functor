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
package org.apache.commons.functor.generator.loop;

import org.apache.commons.functor.Function;
import org.apache.commons.functor.Procedure;
import org.apache.commons.functor.generator.Generator;
import org.apache.commons.lang3.Validate;

/**
 * Generator that transforms the elements of another Generator.
 *
 * @param <I> the type of elements held in the wrapped generator.
 * @param <E> the type of elements held in this generator.
 * @version $Revision: 1508677 $ $Date: 2013-07-30 19:48:02 -0300 (Tue, 30 Jul 2013) $
 */
public class TransformedGenerator<I, E> extends LoopGenerator<E> {

    /**
     * The Function to apply to each element.
     */
    private final Function<? super I, ? extends E> func;

    /**
     * Create a new TransformedGenerator.
     * @param wrapped Generator to transform
     * @param func Function to apply to each element
     */
    // Even though we are passing a Generator<? extends I> to super, and using
    // it in TransformedGenerator#run, what gets actually passed to the Procedure
    // is a <? extends E>, returned by func.
    @SuppressWarnings("unchecked")
    public TransformedGenerator(Generator<? extends I> wrapped, Function<? super I, ? extends E> func) {
        super((Generator<? extends E>) Validate.notNull(wrapped, "Generator argument was null"));
        this.func = Validate.notNull(func, "Function argument was null");
    }

    /**
     * {@inheritDoc}
     */
    // See comment above in the public constructor
    @SuppressWarnings("unchecked")
    public void run(final Procedure<? super E> proc) {
        ((Generator<? extends I>) getWrappedGenerator()).run(new Procedure<I>() {
            public void run(I obj) {
                proc.run(func.evaluate(obj));
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TransformedGenerator<?, ?>)) {
            return false;
        }
        TransformedGenerator<?, ?> other = (TransformedGenerator<?, ?>) obj;
        return other.getWrappedGenerator().equals(getWrappedGenerator()) && other.func == func;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = "TransformedGenerator".hashCode();
        result <<= 2;
        Generator<?> gen = getWrappedGenerator();
        result ^= gen.hashCode();
        result <<= 2;
        result ^= func.hashCode();
        return result;
    }
}
