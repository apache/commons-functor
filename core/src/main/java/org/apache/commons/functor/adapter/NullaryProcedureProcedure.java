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
 * {@link NullaryProcedure Procedure}
 * to the
 * {@link Procedure Procedure} interface
 * by ignoring the arguments.
 *
 * @param <A> the argument type.
 * @version $Revision: 1365377 $ $Date: 2012-07-24 21:59:23 -0300 (Tue, 24 Jul 2012) $
 */
public final class NullaryProcedureProcedure<A> implements Procedure<A> {
    /** The {@link NullaryProcedure Procedure} I'm wrapping. */
    private final NullaryProcedure procedure;

    /**
     * Create a new NullaryProcedureProcedure.
     * @param procedure to adapt
     */
    public NullaryProcedureProcedure(NullaryProcedure procedure) {
        this.procedure = Validate.notNull(procedure, "NullaryProcedure argument was null");
    }

    /**
     * {@inheritDoc}
     */
    public void run(A obj) {
        procedure.run();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof NullaryProcedureProcedure<?>)) {
            return false;
        }
        NullaryProcedureProcedure<?> that = (NullaryProcedureProcedure<?>) obj;
        return this.procedure.equals(that.procedure);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "NullaryProcedureProcedure".hashCode();
        hash ^= procedure.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "NullaryProcedureProcedure<" + procedure + ">";
    }

    /**
     * Adapt a NullaryProcedure to the Procedure interface.
     * @param <A> the argument type.
     * @param procedure to adapt
     * @return NullaryProcedureProcedure<A>
     */
    public static <A> NullaryProcedureProcedure<A> adapt(NullaryProcedure procedure) {
        return null == procedure ? null : new NullaryProcedureProcedure<A>(procedure);
    }

}
