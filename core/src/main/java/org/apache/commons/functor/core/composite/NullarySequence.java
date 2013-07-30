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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.functor.NullaryProcedure;

/**
 * A {@link NullaryProcedure NullaryProcedure}
 * that {@link NullaryProcedure#run runs} an ordered
 * sequence of {@link NullaryProcedure NullaryProcedures}.
 * When the sequence is empty, this procedure is does
 * nothing.
 * <p>
 * Note that although this class implements
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if all the
 * underlying functors are.  Attempts to serialize
 * an instance whose delegates are not all
 * <code>Serializable</code> will result in an exception.
 * </p>
 * @version $Revision: 1365329 $ $Date: 2012-07-24 19:34:23 -0300 (Tue, 24 Jul 2012) $
 */
public class NullarySequence implements NullaryProcedure, Serializable {

    /**
     * serialVersionUID declaration.
     */
    private static final long serialVersionUID = 8041703589149547883L;
    // attributes
    // ------------------------------------------------------------------------
    /**
     * The data structure where storing procedures sequence.
     */
    private List<NullaryProcedure> list = new ArrayList<NullaryProcedure>();

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new NullarySequence.
     */
    public NullarySequence() {
        super();
    }

    /**
     * Create a new NullarySequence instance.
     *
     * @param procedures to run sequentially
     */
    public NullarySequence(NullaryProcedure... procedures) {
        this();
        if (procedures != null) {
            for (NullaryProcedure p : procedures) {
                then(p);
            }
        }
    }

    /**
     * Create a new NullarySequence instance.
     *
     * @param procedures to run sequentially
     */
    public NullarySequence(Iterable<NullaryProcedure> procedures) {
        this();
        if (procedures != null) {
            for (NullaryProcedure p : procedures) {
                then(p);
            }
        }
    }

    // modifiers
    // ------------------------------------------------------------------------
    /**
     * Fluently add a NullaryProcedure.
     * @param p NullaryProcedure to add
     * @return this
     */
    public final NullarySequence then(NullaryProcedure p) {
        if (p != null) {
            list.add(p);
        }
        return this;
    }

    // predicate interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public final void run() {
        for (Iterator<NullaryProcedure> iter = list.iterator(); iter.hasNext();) {
            iter.next().run();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(Object that) {
        return that == this || (that instanceof NullarySequence && equals((NullarySequence) that));
    }

    /**
     * Learn whether a given NullarySequence is equal to this.
     * @param that NullarySequence to test
     * @return boolean
     */
    public boolean equals(NullarySequence that) {
        // by construction, list is never null
        return null != that && list.equals(that.list);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        // by construction, list is never null
        return "NullarySequence".hashCode() ^ list.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "NullarySequence<" + list + ">";
    }

}
