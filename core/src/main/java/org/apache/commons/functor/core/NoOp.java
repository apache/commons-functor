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
package org.apache.commons.functor.core;

import org.apache.commons.functor.BinaryProcedure;
import org.apache.commons.functor.NullaryProcedure;
import org.apache.commons.functor.Procedure;

/**
 * A procedure that does nothing at all.
 * <p>
 * Note that this class implements {@link Procedure},
 * {@link Procedure}, and {@link BinaryProcedure}.
 * </p>
 * @version $Revision$ $Date$
 */
public final class NoOp implements NullaryProcedure, Procedure<Object>, BinaryProcedure<Object, Object> {
    // static attributes
    // ------------------------------------------------------------------------
    /**
     * Basic NoOp instance.
     */
    public static final NoOp INSTANCE = new NoOp();

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new NoOp.
     */
    public NoOp() {
    }

    // predicate interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public void run() {
    }

    /**
     * {@inheritDoc}
     */
    public void run(Object obj) {
    }

    /**
     * {@inheritDoc}
     */
    public void run(Object left, Object right) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object that) {
        return (that instanceof NoOp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return "NoOp".hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "NoOp";
    }

    // static methods
    // ------------------------------------------------------------------------
    /**
     * Get a NoOp instance.
     * @return NoOp
     */
    public static NoOp instance() {
        return INSTANCE;
    }

    /**
     * Get a typed NoOp {@link Procedure}.
     * @param <A> type
     * @return <code>Procedure&lt;A&gt;</code>
     */
    @SuppressWarnings("unchecked")
    public static <A> Procedure<A> unaryInstance() {
        return (Procedure<A>) INSTANCE;
    }

    /**
     * Get a typed NoOp {@link BinaryProcedure}.
     * @param <L> left type
     * @param <R> right type
     * @return <code>BinaryProcedure&lt;L, R&gt;</code>
     */
    @SuppressWarnings("unchecked")
    public static <L, R> BinaryProcedure<L, R> binaryInstance() {
        return (BinaryProcedure<L, R>) INSTANCE;
    }
}
