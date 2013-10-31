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
import org.apache.commons.lang3.Validate;

/**
 * Abstract base class for {@link WhileDoNullaryProcedure} and {@link DoWhileNullaryProcedure}
 * used to implement loop procedures.
 * <p>
 * @version $Revision: 1365329 $ $Date: 2012-07-24 19:34:23 -0300 (Tue, 24 Jul 2012) $
 */
public abstract class AbstractLoopNullaryProcedure implements NullaryProcedure {

    /** Base hash integer used to shift hash. */
    private static final int HASH_SHIFT = 4;

    /**
     * The condition has to be verified that while true,
     * forces the action repetition.
     */
    private final NullaryPredicate condition;

    /**
     * The action to be repeated until the condition is true.
     */
    private final NullaryProcedure action;

    /**
     * Create a new AbstractLoopNullaryProcedure.
     * @param condition while true, repeat
     * @param action loop body
     */
    protected AbstractLoopNullaryProcedure(NullaryPredicate condition, NullaryProcedure action) {
        this.condition = Validate.notNull(condition, "NullaryProcedure argument must not be null");
        this.action = Validate.notNull(action, "NullaryProcedure argument must not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof AbstractLoopNullaryProcedure)) {
            return false;
        }
        AbstractLoopNullaryProcedure that = (AbstractLoopNullaryProcedure) object;
        return (getCondition().equals(that.getCondition())
                && (getAction().equals(that.getAction())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return hashCode("AbstractLoopNullaryProcedure".hashCode());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getClass().getName() + "<" + getCondition() + "," + getAction() + ">";
    }

    /**
     * Create a hashCode by manipulating an input hashCode and factoring in instance state.
     * @param hash incoming hashCode
     * @return int
     */
    protected int hashCode(int hash) {
        hash <<= HASH_SHIFT;
        hash ^= getAction().hashCode();
        hash <<= HASH_SHIFT;
        hash ^= getCondition().hashCode();
        return hash;
    }

    /**
     * Get the condition.
     * @return NullaryPredicate
     */
    protected final NullaryPredicate getCondition() {
        return condition;
    }

    /**
     * Get the action.
     * @return NullaryProcedure
     */
    protected final NullaryProcedure getAction() {
        return action;
    }

}
