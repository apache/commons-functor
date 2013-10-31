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

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.BinaryProcedure;
import org.apache.commons.functor.Procedure;
import org.apache.commons.lang3.Validate;

/**
 * A BinaryProcedure composed of a BinaryFunction whose result is then run through a Procedure.
 *
 * @param <L> the left argument type.
 * @param <R> the right argument type.
 * @version $Revision$ $Date$
 */
public class TransformedBinaryProcedure<L, R> implements BinaryProcedure<L, R> {
    /**
     * Type-remembering helper.
     *
     * @param <X> the wrapped procedure argument.
     */
    private static final class Helper<X, L, R> implements BinaryProcedure<L, R> {
        /**
         * The wrapped function.
         */
        private BinaryFunction<? super L, ? super R, ? extends X> function;
        /**
         * The wrapped procedure.
         */
        private Procedure<? super X> procedure;

        /**
         * Create a new Helper.
         * @param function BinaryFunction
         * @param procedure Function
         */
        private Helper(BinaryFunction<? super L, ? super R, ? extends X> function,
                Procedure<? super X> procedure) {
            this.function = Validate.notNull(function, "BinaryFunction argument was null");
            this.procedure = Validate.notNull(procedure, "Procedure argument was null");
        }

        /**
         * {@inheritDoc}
         */
        public void run(L left, R right) {
            procedure.run(function.evaluate(left, right));
        }
    }

    /**
     * The adapted helper.
     */
    private final Helper<?, L, R> helper;

    /**
     * Create a new TransformedBinaryProcedure.
     * @param <X> the wrapped procedure argument.
     * @param function BinaryFunction
     * @param procedure Procedure
     */
    public <X> TransformedBinaryProcedure(BinaryFunction<? super L, ? super R, ? extends X> function,
            Procedure<? super X> procedure) {
        this.helper = new Helper<X, L, R>(function, procedure);
    }

    /**
     * {@inheritDoc}
     */
    public final void run(L left, R right) {
        helper.run(left, right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TransformedBinaryProcedure<?, ?>)) {
            return false;
        }
        TransformedBinaryProcedure<?, ?> that = (TransformedBinaryProcedure<?, ?>) obj;
        return this.helper.function.equals(that.helper.function)
                && this.helper.procedure.equals(that.helper.procedure);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = "TransformedBinaryProcedure".hashCode();
        result <<= 2;
        result |= helper.procedure.hashCode();
        result <<= 2;
        result |= helper.function.hashCode();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TransformedBinaryProcedure<" + helper.function + "; " + helper.procedure + ">";
    }
}
