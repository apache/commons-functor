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

import org.apache.commons.functor.BinaryProcedure;
import org.apache.commons.functor.Procedure;
import org.apache.commons.lang3.Validate;

/**
 * Adapts a
 * {@link Procedure Procedure}
 * to the
 * {@link BinaryProcedure BinaryProcedure} interface
 * by ignoring the first binary argument.
 *
 * @param <L> the left argument type.
 * @param <R> the right argument type.
 * @version $Revision$ $Date$
 */
public final class IgnoreLeftProcedure<L, R> implements BinaryProcedure<L, R> {
    /** The {@link Procedure Procedure} I'm wrapping. */
    private final Procedure<? super R> procedure;

    /**
     * Create a new IgnoreLeftProcedure.
     * @param procedure to adapt
     */
    public IgnoreLeftProcedure(Procedure<? super R> procedure) {
        this.procedure = Validate.notNull(procedure, "Procedure argument was null");
    }

    /**
     * {@inheritDoc}
     */
    public void run(L left, R right) {
        procedure.run(right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof IgnoreLeftProcedure<?, ?>)) {
            return false;
        }
        IgnoreLeftProcedure<?, ?> that = (IgnoreLeftProcedure<?, ?>) obj;
        return this.procedure.equals(that.procedure);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "IgnoreLeftProcedure".hashCode();
        hash ^= procedure.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "IgnoreLeftProcedure<" + procedure + ">";
    }

    /**
     * Adapt a Procedure to the BinaryProcedure interface.
     * @param <L> left type
     * @param <R> right type
     * @param procedure to adapt
     * @return IgnoreLeftProcedure<L, R>
     */
    public static <L, R> IgnoreLeftProcedure<L, R> adapt(Procedure<? super R> procedure) {
        return null == procedure ? null : new IgnoreLeftProcedure<L, R>(procedure);
    }

}
