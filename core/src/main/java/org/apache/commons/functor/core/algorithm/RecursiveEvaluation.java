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

import org.apache.commons.functor.NullaryFunction;
import org.apache.commons.lang3.Validate;

/**
 * Tail recursion for {@link NullaryFunction functions}. If the {@link NullaryFunction}
 * returns another function of type <code>functionType</code>, that function
 * is executed. Functions are executed until a non function value or a
 * function of a type other than that expected is returned.
 */
public class RecursiveEvaluation implements NullaryFunction<Object> {
    /**
     * The initial recursive NullaryFunction type.
     */
    private final Class<?> functionType;
    /**
     * The initial, potentially recursive NullaryFunction.
     */
    private NullaryFunction<?> function;

    /**
     * Create a new RecursiveEvaluation. Recursion will continue while the
     * returned value is of the same runtime class as <code>function</code>.
     * @param function initial, potentially recursive NullaryFunction
     */
    public RecursiveEvaluation(NullaryFunction<?> function) {
        this(function, getClass(function));
    }

    /**
     * Create a new RecursiveEvaluation.
     * @param function initial, potentially recursive NullaryFunction
     * @param functionType as long as result is an instance, keep processing.
     */
    public RecursiveEvaluation(NullaryFunction<?> function, Class<?> functionType) {
        Validate.notNull(function, "NullaryFunction argument was null");
        if (!NullaryFunction.class.isAssignableFrom(functionType)) {
            throw new IllegalArgumentException(NullaryFunction.class + " is not assignable from " + functionType);
        }
        this.function = function;
        this.functionType = Validate.notNull(functionType, "FunctionType argument was null");
    }

    /**
     * {@inheritDoc}
     */
    public final Object evaluate() {
        Object result = null;
        // if the function returns another function, execute it. stop executing
        // when the result is not of the expected type.
        while (true) {
            result = function.evaluate();
            if (functionType.isInstance(result)) {
                function = (NullaryFunction<?>) result;
                continue;
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RecursiveEvaluation)) {
            return false;
        }
        return ((RecursiveEvaluation) obj).function.equals(function);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return "RecursiveEvaluation".hashCode() << 2 ^ function.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "RecursiveEvaluation<" + functionType + "," + function + ">";
    }

    /**
     * Get the class of the specified object, or <code>null</code> if <code>o</code> is <code>null</code>.
     * @param f Object to check
     * @return Class found
     */
    private static Class<?> getClass(NullaryFunction<?> f) {
        return f == null ? null : f.getClass();
    }
}
