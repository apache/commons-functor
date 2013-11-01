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
 * {@link BinaryProcedure BinaryProcedure}
 * to the
 * {@link Procedure Procedure} interface
 * using a constant left-side argument.
 *
 * @param <A> the argument type.
 * @version $Revision$ $Date$
 */
public final class LeftBoundProcedure<A> implements Procedure<A> {
    /** The {@link BinaryProcedure BinaryProcedure} I'm wrapping. */
    private final BinaryProcedure<Object, ? super A> procedure;
    /** The parameter to pass to {@code procedure}. */
    private final Object param;

    /**
     * Create a new LeftBoundProcedure.
     * @param <L> the left argument type.
     * @param procedure the procedure to adapt
     * @param arg the constant argument to use
     */
    @SuppressWarnings("unchecked")
    public <L> LeftBoundProcedure(BinaryProcedure<? super L, ? super A> procedure, L arg) {
        this.procedure =
            (BinaryProcedure<Object, ? super A>) Validate.notNull(procedure,
                "BinaryProcedure argument was null");
        this.param = arg;
    }

    /**
     * {@inheritDoc}
     */
    public void run(A obj) {
        procedure.run(param, obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LeftBoundProcedure<?>)) {
            return false;
        }
        LeftBoundProcedure<?> that = (LeftBoundProcedure<?>) obj;
        return procedure.equals(that.procedure)
                && (null == param ? null == that.param : param.equals(that.param));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "LeftBoundProcedure".hashCode();
        hash <<= 2;
        hash ^= procedure.hashCode();
        if (null != param) {
            hash <<= 2;
            hash ^= param.hashCode();
        }
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "LeftBoundProcedure<" + procedure + "(" + param + ",?)>";
    }

    /**
     * Get a Procedure from <code>procedure</code>.
     * @param <L> left type
     * @param <R> right type
     * @param procedure to adapt
     * @param arg left side argument
     * @return LeftBoundProcedure<R>
     */
    public static <L, R> LeftBoundProcedure<R> bind(BinaryProcedure<? super L, ? super R> procedure, L arg) {
        return null == procedure ? null : new LeftBoundProcedure<R>(procedure, arg);
    }

}
