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

import org.apache.commons.functor.NullaryPredicate;
import org.apache.commons.functor.NullaryProcedure;
import org.apache.commons.functor.core.NoOp;
import org.apache.commons.lang3.Validate;

/**
 * A {@link NullaryProcedure NullaryProcedure}
 * similiar to Java's "ternary"
 * or "conditional" operator (<code>&#x3F; &#x3A;</code>).
 * Given a {@link NullaryPredicate predicate}
 * <i>p</i> and {@link NullaryProcedure procedures}
 * <i>q</i> and <i>r</i>, {@link #run runs}
 * <code>if (p.test()) { q.run(); } else { r.run(); }</code>.
 * @version $Revision: 1365329 $ $Date: 2012-07-24 19:34:23 -0300 (Tue, 24 Jul 2012) $
 */
public final class ConditionalNullaryProcedure implements NullaryProcedure {
    /** Base hash integer used to shift hash. */
    private static final int HASH_SHIFT = 4;

    // attributes
    // ------------------------------------------------------------------------
    /**
     * the condition to be evaluated.
     */
    private final NullaryPredicate ifPred;
    /**
     * the procedure executed if the condition is satisfied.
     */
    private final NullaryProcedure thenProc;
    /**
     * the procedure executed if the condition is not satisfied.
     */
    private final NullaryProcedure elseProc;

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new ConditionalNullaryProcedure.
     * @param ifPred if
     * @param thenProc then
     */
    public ConditionalNullaryProcedure(NullaryPredicate ifPred, NullaryProcedure thenProc) {
        this(ifPred, thenProc, NoOp.instance());
    }

    /**
     * Create a new ConditionalNullaryProcedure.
     * @param ifPred if
     * @param thenProc then
     * @param elseProc else
     */
    public ConditionalNullaryProcedure(NullaryPredicate ifPred, NullaryProcedure thenProc, NullaryProcedure elseProc) {
        this.ifPred = Validate.notNull(ifPred, "NullaryPredicate argument was null");
        this.thenProc = Validate.notNull(thenProc, "'then' NullaryProcedure argument was null");
        this.elseProc = Validate.notNull(elseProc, "'else' NullaryProcedure argument was null");
    }

    // predicate interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public void run() {
        if (ifPred.test()) {
            thenProc.run();
        } else {
            elseProc.run();
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
        if (!(obj instanceof ConditionalNullaryProcedure)) {
            return false;
        }
        ConditionalNullaryProcedure that = (ConditionalNullaryProcedure) obj;
        return this.ifPred.equals(that.ifPred)
                && this.thenProc.equals(that.thenProc)
                && this.elseProc.equals(that.elseProc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "ConditionalNullaryProcedure".hashCode();
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
        return "ConditionalNullaryProcedure<" + ifPred + "?" + thenProc + ":" + elseProc + ">";
    }

}
