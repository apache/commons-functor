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

import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.UnaryProcedure;
import org.apache.commons.functor.core.NoOp;
import org.apache.commons.lang3.Validate;

/**
 * A {@link UnaryProcedure UnaryProcedure}
 * similiar to Java's "ternary"
 * or "conditional" operator (<code>&#x3F; &#x3A;</code>).
 * Given a {@link UnaryPredicate predicate}
 * <i>p</i> and {@link UnaryProcedure procedures}
 * <i>q</i> and <i>r</i>, {@link #run runs}
 * <code>if (p.test(x)) { q.run(x); } else { r.run(x); }</code>.
 * <p>
 * Note that although this class implements
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if all the
 * underlying functors are.  Attempts to serialize
 * an instance whose delegates are not all
 * <code>Serializable</code> will result in an exception.
 * </p>
 * @param <A> the argument type.
 * @version $Revision$ $Date$
 */
public final class ConditionalUnaryProcedure<A> implements UnaryProcedure<A>, Serializable {
    /**
     * serialVersionUID declaration.
     */
    private static final long serialVersionUID = -895833369740247391L;

    /** Base hash integer used to shift hash. */
    private static final int HASH_SHIFT = 4;
    // attributes
    // ------------------------------------------------------------------------
    /**
     * the condition to be evaluated.
     */
    private final UnaryPredicate<? super A> ifPred;
    /**
     * the procedure executed if the condition is satisfied.
     */
    private final UnaryProcedure<? super A> thenProc;
    /**
     * the procedure executed if the condition is not satisfied.
     */
    private final UnaryProcedure<? super A> elseProc;

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new ConditionalUnaryProcedure.
     * @param ifPred if
     * @param thenProc then
     */
    public ConditionalUnaryProcedure(UnaryPredicate<? super A> ifPred, UnaryProcedure<? super A> thenProc) {
        this(ifPred, thenProc, NoOp.instance());
    }

    /**
     * Create a new ConditionalUnaryProcedure.
     * @param ifPred if
     * @param thenProc then
     * @param elseProc else
     */
    public ConditionalUnaryProcedure(UnaryPredicate<? super A> ifPred,
            UnaryProcedure<? super A> thenProc,
            UnaryProcedure<? super A> elseProc) {
        this.ifPred = Validate.notNull(ifPred, "UnaryPredicate argument was null");
        this.thenProc = Validate.notNull(thenProc, "'then' UnaryProcedure argument was null");
        this.elseProc = Validate.notNull(elseProc, "'else' UnaryProcedure argument was null");
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
    public boolean equals(Object that) {
        return that == this || (that instanceof ConditionalUnaryProcedure<?>
                                    && equals((ConditionalUnaryProcedure<?>) that));
    }

    /**
     * Learn whether another ConditionalUnaryProcedure is equal to this.
     * @param that ConditionalUnaryProcedure to test
     * @return boolean
     */
    public boolean equals(ConditionalUnaryProcedure<?> that) {
        return null != that
                && ifPred.equals(that.ifPred)
                && thenProc.equals(that.thenProc)
                && elseProc.equals(that.elseProc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "ConditionalUnaryProcedure".hashCode();
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
        return "ConditionalUnaryProcedure<" + ifPred + "?" + thenProc + ":" + elseProc + ">";
    }

}
