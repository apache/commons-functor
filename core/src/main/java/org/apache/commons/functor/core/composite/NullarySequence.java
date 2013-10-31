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

import org.apache.commons.functor.NullaryProcedure;

/**
 * A {@link NullaryProcedure NullaryProcedure}
 * that {@link NullaryProcedure#run runs} an ordered
 * sequence of {@link NullaryProcedure NullaryProcedures}.
 * When the sequence is empty, this procedure is does
 * nothing.
 * @version $Revision: 1365329 $ $Date: 2012-07-24 19:34:23 -0300 (Tue, 24 Jul 2012) $
 */
public class NullarySequence implements NullaryProcedure {

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
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NullarySequence)) {
            return false;
        }
        NullarySequence that = (NullarySequence) obj;
        return this.list.equals(that.list);
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
