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

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.Function;
import org.apache.commons.lang3.Validate;

/**
 * Adapts a BinaryFunction as a Function by sending the same argument to both sides of the BinaryFunction.
 * It sounds nonsensical, but using Composite functions, can be made to do something useful.
 * @param <A> the argument type.
 * @param <T> the returned value type.
 * @version $Revision: 1345136 $ $Date: 2012-06-01 09:47:06 -0300 (Fri, 01 Jun 2012) $
 */
public final class BinaryFunctionFunction<A, T> implements Function<A, T> {
    /**
     * The adapted function.
     */
    private final BinaryFunction<? super A, ? super A, ? extends T> function;

    /**
     * Create a new BinaryFunctionFunction.
     * @param function to adapt
     */
    public BinaryFunctionFunction(BinaryFunction<? super A, ? super A, ? extends T> function) {
        this.function = Validate.notNull(function, "BinaryFunction argument was null");
    }

    /**
     * {@inheritDoc}
     */
    public T evaluate(A obj) {
        return function.evaluate(obj, obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BinaryFunctionFunction<?, ?>)) {
            return false;
        }
        BinaryFunctionFunction<?, ?> that = (BinaryFunctionFunction<?, ?>) obj;
        return this.function.equals(that.function);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return ("BinaryFunctionFunction".hashCode() << 2) | function.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BinaryFunctionFunction<" + function + ">";
    }

    /**
     * Adapt a BinaryFunction as a Function.
     * @param <A> input type
     * @param <T> result type
     * @param function to adapt
     * @return Function<A, T>
     */
    public static <A, T> Function<A, T> adapt(BinaryFunction<? super A, ? super A, ? extends T> function) {
        return null == function ? null : new BinaryFunctionFunction<A, T>(function);
    }

}
