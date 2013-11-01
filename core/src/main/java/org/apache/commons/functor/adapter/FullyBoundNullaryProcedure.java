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
import org.apache.commons.functor.NullaryProcedure;
import org.apache.commons.lang3.Validate;

/**
 * Adapts a
 * {@link BinaryProcedure BinaryProcedure}
 * to the
 * {@link NullaryProcedure NullaryProcedure} interface
 * using a constant left-side argument.
 *
 * @version $Revision: 1365377 $ $Date: 2012-07-24 21:59:23 -0300 (Tue, 24 Jul 2012) $
 */
public final class FullyBoundNullaryProcedure implements NullaryProcedure {
    /** The {@link BinaryProcedure BinaryProcedure} I'm wrapping. */
    private final BinaryProcedure<Object, Object> procedure;
    /** The left parameter to pass to {@code procedure}. */
    private final Object left;
    /** The right parameter to pass to {@code procedure}. */
    private final Object right;

    /**
     * Create a new FullyBoundNullaryProcedure instance.
     * @param <L> left type
     * @param <R> right type
     * @param procedure the procedure to adapt
     * @param left the left argument to use
     * @param right the right argument to use
     */
    @SuppressWarnings("unchecked")
    public <L, R> FullyBoundNullaryProcedure(BinaryProcedure<? super L, ? super R> procedure, L left, R right) {
        this.procedure =
            (BinaryProcedure<Object, Object>) Validate.notNull(procedure,
                "BinaryProcedure argument was null");
        this.left = left;
        this.right = right;
    }

    /**
     * {@inheritDoc}
     */
    public void run() {
        procedure.run(left, right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FullyBoundNullaryProcedure)) {
            return false;
        }
        FullyBoundNullaryProcedure that = (FullyBoundNullaryProcedure) obj;
        return procedure.equals(that.procedure)
                && (null == left ? null == that.left : left.equals(that.left))
                && (null == right ? null == that.right : right.equals(that.right));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "FullyBoundNullaryProcedure".hashCode();
        hash <<= 2;
        hash ^= procedure.hashCode();
        hash <<= 2;
        if (null != left) {
            hash ^= left.hashCode();
        }
        hash <<= 2;
        if (null != right) {
            hash ^= right.hashCode();
        }
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FullyBoundNullaryProcedure<" + procedure + "(" + left + ", " + right + ")>";
    }

    /**
     * Adapt a BinaryNullaryProcedure to the NullaryProcedure interface.
     * @param <L> left type
     * @param <R> right type
     * @param procedure to adapt
     * @param left left side argument
     * @param right right side argument
     * @return FullyBoundNullaryProcedure
     */
    public static <L, R> FullyBoundNullaryProcedure bind(
            BinaryProcedure<? super L, ? super R> procedure, L left, R right) {
        return null == procedure ? null : new FullyBoundNullaryProcedure(procedure, left, right);
    }

}
