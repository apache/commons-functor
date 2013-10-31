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

import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.Procedure;
import org.apache.commons.functor.core.NoOp;
import org.apache.commons.lang3.Validate;

/**
 * A {@link Procedure Procedure}
 * similiar to Java's "ternary"
 * or "conditional" operator (<code>&#x3F; &#x3A;</code>).
 * Given a {@link Predicate predicate}
 * <i>p</i> and {@link Procedure procedures}
 * <i>q</i> and <i>r</i>, {@link #run runs}
 * <code>if (p.test(x)) { q.run(x); } else { r.run(x); }</code>.
 * @param <A> the argument type.
 * @version $Revision$ $Date$
 */
public final class ConditionalProcedure<A> implements Procedure<A> {
    /** Base hash integer used to shift hash. */
    private static final int HASH_SHIFT = 4;
    // attributes
    // ------------------------------------------------------------------------
    /**
     * the condition to be evaluated.
     */
    private final Predicate<? super A> ifPred;
    /**
     * the procedure executed if the condition is satisfied.
     */
    private final Procedure<? super A> thenProc;
    /**
     * the procedure executed if the condition is not satisfied.
     */
    private final Procedure<? super A> elseProc;

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new ConditionalProcedure.
     * @param ifPred if
     * @param thenProc then
     */
    public ConditionalProcedure(Predicate<? super A> ifPred, Procedure<? super A> thenProc) {
        this(ifPred, thenProc, NoOp.instance());
    }

    /**
     * Create a new ConditionalProcedure.
     * @param ifPred if
     * @param thenProc then
     * @param elseProc else
     */
    public ConditionalProcedure(Predicate<? super A> ifPred,
            Procedure<? super A> thenProc,
            Procedure<? super A> elseProc) {
        this.ifPred = Validate.notNull(ifPred, "Predicate argument was null");
        this.thenProc = Validate.notNull(thenProc, "'then' Procedure argument was null");
        this.elseProc = Validate.notNull(elseProc, "'else' Procedure argument was null");
    }

    // predicate interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public void run(A obj) {
        if (ifPred.test(obj)) {
            thenProc.run(obj);
        } else {
            elseProc.run(obj);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ConditionalProcedure<?>)) {
            return false;
        }
        ConditionalProcedure<?> that = (ConditionalProcedure<?>) obj;
        return this.ifPred.equals(that.ifPred)
                && this.thenProc.equals(that.thenProc)
                && this.elseProc.equals(that.elseProc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "ConditionalProcedure".hashCode();
        hash <<= HASH_SHIFT;
        hash ^= ifPred.hashCode();
        hash <<= HASH_SHIFT;
        hash ^= thenProc.hashCode();
        hash <<= HASH_SHIFT;
        hash ^= elseProc.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ConditionalProcedure<" + ifPred + "?" + thenProc + ":" + elseProc + ">";
    }

}
