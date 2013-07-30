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

import java.io.Serializable;

import org.apache.commons.functor.NullaryFunction;
import org.apache.commons.functor.NullaryProcedure;
import org.apache.commons.functor.Procedure;
import org.apache.commons.lang3.Validate;

/**
 * A NullaryProcedure composed of a NullaryFunction whose result is then run through a Procedure.
 * @version $Revision: 1365329 $ $Date: 2012-07-24 19:34:23 -0300 (Tue, 24 Jul 2012) $
 */
public class TransformedNullaryProcedure implements NullaryProcedure, Serializable {
    /**
     * serialVersionUID declaration.
     */
    private static final long serialVersionUID = -4111958123789033410L;

    /** Base hash integer used to shift hash. */
    private static final int HASH_SHIFT = 2;

    /**
     * Type-remembering helper.
     * @param <X> the adapted function argument type.
     */
    private static final class Helper<X> implements NullaryProcedure, Serializable {
        /**
         * serialVersionUID declaration.
         */
        private static final long serialVersionUID = -4093503167446891318L;
        /**
         * The adapted function.
         */
        private NullaryFunction<? extends X> function;
        /**
         * The adapted procedure.
         */
        private Procedure<? super X> procedure;

        /**
         * Create a new Helper.
         * @param function NullaryFunction
         * @param procedure Procedure
         */
        private Helper(NullaryFunction<? extends X> function, Procedure<? super X> procedure) {
            this.function = Validate.notNull(function, "NullaryFunction argument must not be null");
            this.procedure = Validate.notNull(procedure, "Procedure argument must not be null");
        }

        /**
         * {@inheritDoc}
         */
        public void run() {
            procedure.run(function.evaluate());
        }
    }

    /**
     * The adapted helper.
     */
    private final Helper<?> helper;

    /**
     * Create a new TransformedNullaryProcedure.
     * @param <X> the adapted function argument type.
     * @param function NullaryFunction
     * @param procedure Procedure
     */
    public <X> TransformedNullaryProcedure(NullaryFunction<? extends X> function, Procedure<? super X> procedure) {
        this.helper = new Helper<X>(function, procedure);
    }

    /**
     * {@inheritDoc}
     */
    public final void run() {
        helper.run();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(Object obj) {
        return obj == this || obj instanceof TransformedNullaryProcedure
                && equals((TransformedNullaryProcedure) obj);
    }

    /**
     * Learn whether another TransformedNullaryProcedure is equal to <code>this</code>.
     * @param that instance to test
     * @return whether equal
     */
    public final boolean equals(TransformedNullaryProcedure that) {
        return that != null && that.helper.function.equals(this.helper.function)
                && that.helper.procedure.equals(this.helper.procedure);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = "TransformedNullaryProcedure".hashCode();
        result <<= HASH_SHIFT;
        result |= helper.procedure.hashCode();
        result <<= HASH_SHIFT;
        result |= helper.function.hashCode();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TransformedNullaryProcedure<" + helper.function + "; " + helper.procedure + ">";
    }
}
