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

import org.apache.commons.functor.NullaryProcedure;
import org.apache.commons.functor.Procedure;
import org.apache.commons.lang3.Validate;

/**
 * Adapts a
 * {@link Procedure Procedure}
 * to the
 * {@link NullaryProcedure NullaryProcedure} interface
 * using a constant unary argument.
 *
 * @version $Revision: 1365377 $ $Date: 2012-07-24 21:59:23 -0300 (Tue, 24 Jul 2012) $
 */
public final class BoundNullaryProcedure implements NullaryProcedure {
    /** The {@link Procedure Procedure} I'm wrapping. */
    private final Procedure<Object> procedure;
    /** The parameter to pass to {@code procedure}. */
    private final Object param;

    /**
     * Create a new BoundNullaryProcedure instance.
     * @param <A> arg type
     * @param procedure the procedure to adapt
     * @param arg the constant argument to use
     */
    @SuppressWarnings("unchecked")
    public <A> BoundNullaryProcedure(Procedure<? super A> procedure, A arg) {
        this.procedure =
            (Procedure<Object>) Validate.notNull(procedure,
                "Procedure argument was null");
        this.param = arg;
    }

    /**
     * {@inheritDoc}
     */
    public void run() {
        procedure.run(param);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BoundNullaryProcedure)) {
            return false;
        }
        BoundNullaryProcedure that = (BoundNullaryProcedure) obj;
        return procedure.equals(that.procedure)
                && (null == param ? null == that.param : param.equals(that.param));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "BoundNullaryProcedure".hashCode();
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
        return "BoundNullaryProcedure<" + procedure + "(" + param + ")>";
    }

    /**
     * Adapt the given, possibly-<code>null</code>,
     * {@link Procedure Procedure} to the
     * {@link NullaryProcedure NullaryProcedure} interface by binding
     * the specified <code>Object</code> as a constant
     * argument.
     * When the given <code>Procedure</code> is <code>null</code>,
     * returns <code>null</code>.
     *
     * @param <A> arg type
     * @param procedure the possibly-<code>null</code>
     *        {@link Procedure Procedure} to adapt
     * @param arg the object to bind as a constant argument
     * @return a <code>BoundNullaryProcedure</code> wrapping the given
     *         {@link Procedure Procedure}, or <code>null</code>
     *         if the given <code>Procedure</code> is <code>null</code>
     */
    public static <A> BoundNullaryProcedure bind(Procedure<? super A> procedure, A arg) {
        return null == procedure ? null : new BoundNullaryProcedure(procedure, arg);
    }

}
