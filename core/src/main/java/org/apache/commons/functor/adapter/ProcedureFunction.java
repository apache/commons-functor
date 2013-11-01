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

import org.apache.commons.functor.Function;
import org.apache.commons.functor.Procedure;
import org.apache.commons.lang3.Validate;

/**
 * Adapts a
 * {@link Procedure Procedure}
 * to the
 * {@link Function Function} interface
 * by always returning <code>null</code>.
 *
 * @param <A> the argument type.
 * @param <T> the returned value type.
 * @version $Revision$ $Date$
 */
public final class ProcedureFunction<A, T> implements Function<A, T> {
    /** The {@link Procedure Procedure} I'm wrapping. */
    private final Procedure<? super A> procedure;

    /**
     * Create a new ProcedureFunction.
     * @param procedure to adapt
     */
    public ProcedureFunction(Procedure<? super A> procedure) {
        this.procedure = Validate.notNull(procedure, "Procedure argument was null");
    }

    /**
     * {@inheritDoc}
     */
    public T evaluate(A obj) {
        procedure.run(obj);
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
        if (!(obj instanceof ProcedureFunction<?, ?>)) {
            return false;
        }
        ProcedureFunction<?, ?> that = (ProcedureFunction<?, ?>) obj;
        return this.procedure.equals(that.procedure);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "ProcedureFunction".hashCode();
        hash ^= procedure.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ProcedureFunction<" + procedure + ">";
    }

    /**
     * Adapt a Procedure to the Function interface.
     * @param <A> the argument type.
     * @param <T> the returned value type.
     * @param procedure to adapt
     * @return ProcedureFunction
     */
    public static <A, T> ProcedureFunction<A, T> adapt(Procedure<? super A> procedure) {
        return null == procedure ? null : new ProcedureFunction<A, T>(procedure);
    }

}
