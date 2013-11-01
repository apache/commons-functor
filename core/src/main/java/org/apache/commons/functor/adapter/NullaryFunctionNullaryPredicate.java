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

import org.apache.commons.functor.NullaryFunction;
import org.apache.commons.functor.NullaryPredicate;
import org.apache.commons.lang3.Validate;

/**
 * Adapts a <code>Boolean</code>-valued
 * {@link NullaryFunction NullaryFunction} to the
 * {@link NullaryPredicate NullaryPredicate} interface.
 *
 * @version $Revision: 1365377 $ $Date: 2012-07-24 21:59:23 -0300 (Tue, 24 Jul 2012) $
 */
public final class NullaryFunctionNullaryPredicate implements NullaryPredicate {

    /** The {@link NullaryFunction NullaryFunction} I'm wrapping. */
    private final NullaryFunction<Boolean> function;

    /**
     * Create a new NullaryFunctionNullaryPredicate.
     * @param function to adapt
     */
    public NullaryFunctionNullaryPredicate(NullaryFunction<Boolean> function) {
        this.function = Validate.notNull(function, "NullaryFunction argument was null");
    }

    /**
     * Returns the <code>boolean</code> value of the non-<code>null</code>
     * <code>Boolean</code> returned by the {@link NullaryFunction#evaluate evaluate}
     * method of my underlying function.
     * {@inheritDoc}
     */
    public boolean test() {
        return function.evaluate().booleanValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof NullaryFunctionNullaryPredicate)) {
            return false;
        }
        NullaryFunctionNullaryPredicate that = (NullaryFunctionNullaryPredicate) obj;
        return this.function.equals(that.function);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "NullaryFunctionNullaryPredicate".hashCode();
        hash ^= function.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "NullaryFunctionNullaryPredicate<" + function + ">";
    }

    /**
     * Adapt a NullaryFunction as a NullaryPredicate.
     * @param function to adapt
     * @return NullaryFunctionNullaryPredicate
     */
    public static NullaryFunctionNullaryPredicate adapt(NullaryFunction<Boolean> function) {
        return null == function ? null : new NullaryFunctionNullaryPredicate(function);
    }
}
