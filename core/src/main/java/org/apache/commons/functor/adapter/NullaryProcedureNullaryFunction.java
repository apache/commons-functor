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

import org.apache.commons.functor.NullaryFunction;
import org.apache.commons.functor.NullaryProcedure;
import org.apache.commons.lang3.Validate;

/**
 * Adapts a
 * {@link NullaryProcedure NullaryProcedure}
 * to the
 * {@link NullaryFunction NullaryFunction} interface
 * by always returning <code>null</code>.
 *
 * @param <T> the returned value type.
 * @version $Revision: 1365377 $ $Date: 2012-07-24 21:59:23 -0300 (Tue, 24 Jul 2012) $
 */
public final class NullaryProcedureNullaryFunction<T> implements NullaryFunction<T> {
    /** The {@link NullaryProcedure NullaryProcedure} I'm wrapping. */
    private final NullaryProcedure procedure;

    /**
     * Create a new NullaryProcedureNullaryFunction.
     * @param procedure to adapt
     */
    public NullaryProcedureNullaryFunction(NullaryProcedure procedure) {
        this.procedure = Validate.notNull(procedure, "NullaryProcedure argument was null");
    }

    /**
     * {@inheritDoc}
     */
    public T evaluate() {
        procedure.run();
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof NullaryProcedureNullaryFunction<?>)) {
            return false;
        }
        NullaryProcedureNullaryFunction<?> that = (NullaryProcedureNullaryFunction<?>) obj;
        return this.procedure.equals(that.procedure);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "NullaryProcedureNullaryFunction".hashCode();
        hash ^= procedure.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "NullaryProcedureNullaryFunction<" + procedure + ">";
    }

    /**
     * Adapt a NullaryProcedure as a NullaryFunction.
     * @param <T> the returned value type.
     * @param procedure to adapt
     * @return NullaryProcedureNullaryFunction<T>
     */
    public static <T> NullaryProcedureNullaryFunction<T> adapt(NullaryProcedure procedure) {
        return null == procedure ? null : new NullaryProcedureNullaryFunction<T>(procedure);
    }

}
