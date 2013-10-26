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
 * Adapts a BinaryProcedure as a Procedure by sending the same argument to both sides of the BinaryProcedure.
 * @param <A> the argument type.
 * @version $Revision: 1345136 $ $Date: 2012-06-01 09:47:06 -0300 (Fri, 01 Jun 2012) $
 */
public final class BinaryProcedureProcedure<A> implements Procedure<A> {
    /**
     * The adapted procedure.
     */
    private final BinaryProcedure<? super A, ? super A> procedure;

    /**
     * Create a new BinaryProcedureProcedure.
     * @param procedure to adapt
     */
    public BinaryProcedureProcedure(BinaryProcedure<? super A, ? super A> procedure) {
        this.procedure = Validate.notNull(procedure, "BinaryProcedure argument was null");
    }

    /**
     * {@inheritDoc}
     */
    public void run(A obj) {
        procedure.run(obj, obj);
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BinaryProcedureProcedure<?>)) {
            return false;
        }
        BinaryProcedureProcedure<?> that = (BinaryProcedureProcedure<?>) obj;
        return this.procedure.equals(that.procedure);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return ("BinaryProcedureProcedure".hashCode() << 2) | procedure.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BinaryProcedureProcedure<" + procedure + ">";
    }

    /**
     * Adapt a BinaryProcedure as a Procedure.
     * @param <A> argument type
     * @param procedure BinaryProcedure to adapt
     * @return Procedure<A>
     */
    public static <A> Procedure<A> adapt(BinaryProcedure<? super A, ? super A> procedure) {
        return null == procedure ? null : new BinaryProcedureProcedure<A>(procedure);
    }

}
