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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.functor.BinaryProcedure;

/**
 * A {@link BinaryProcedure BinaryProcedure}
 * that {@link BinaryProcedure#run runs} an ordered
 * sequence of {@link BinaryProcedure BinaryProcedures}.
 * When the sequence is empty, this procedure is does
 * nothing.
 *
 * @param <L> the procedure left argument type.
 * @param <R> the procedure right argument type.
 * @version $Revision$ $Date$
 */
public class BinarySequence<L, R> implements BinaryProcedure<L, R> {
    // attributes
    // ------------------------------------------------------------------------
    /**
     * A list where storing all the procedures references.
     */
    private final List<BinaryProcedure<? super L, ? super R>> list =
        new ArrayList<BinaryProcedure<? super L, ? super R>>();

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new BinarySequence.
     */
    public BinarySequence() {
        super();
    }

    /**
     * Create a new BinarySequence instance.
     *
     * @param procedures to run sequentially
     */
    public BinarySequence(BinaryProcedure<? super L, ? super R>... procedures) {
        this();
        if (procedures != null) {
            for (BinaryProcedure<? super L, ? super R> p : procedures) {
                then(p);
            }
        }
    }

    /**
     * Create a new BinarySequence instance.
     *
     * @param procedures to run sequentially
     */
    public BinarySequence(Iterable<BinaryProcedure<? super L, ? super R>> procedures) {
        this();
        if (procedures != null) {
            for (BinaryProcedure<? super L, ? super R> p : procedures) {
                then(p);
            }
        }
    }

    /**
     * Fluently add a BinaryProcedure.
     * @param p BinaryProcedure to add
     * @return this
     */
    public final BinarySequence<L, R> then(BinaryProcedure<? super L, ? super R> p) {
        list.add(p);
        return this;
    }

    // predicate interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public final void run(L left, R right) {
        for (Iterator<BinaryProcedure<? super L, ? super R>> iter = list.iterator(); iter.hasNext();) {
            iter.next().run(left, right);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BinarySequence<?, ?>)) {
            return false;
        }
        BinarySequence<?, ?> that = (BinarySequence<?, ?>) obj;
        return this.list.equals(that.list);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        // by construction, list is never null
        return "BinarySequence".hashCode() ^ list.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BinarySequence<" + list + ">";
    }

}
