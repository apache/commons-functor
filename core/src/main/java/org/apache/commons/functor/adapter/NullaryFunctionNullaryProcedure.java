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
import org.apache.commons.functor.NullaryProcedure;
import org.apache.commons.lang3.Validate;

/**
 * Adapts a {@link NullaryFunction NullaryFunction}
 * to the {@link NullaryProcedure NullaryProcedure}
 * interface by ignoring the value returned
 * by the function.
 *
 * @version $Revision: 1365377 $ $Date: 2012-07-24 21:59:23 -0300 (Tue, 24 Jul 2012) $
 */
public final class NullaryFunctionNullaryProcedure implements NullaryProcedure {
    /** The {@link NullaryFunction NullaryFunction} I'm wrapping. */
    private final NullaryFunction<?> function;

    /**
     * Create an {@link NullaryProcedure NullaryProcedure} wrapping
     * the given {@link NullaryFunction NullaryFunction}.
     * @param function the {@link NullaryFunction NullaryFunction} to wrap
     */
    public NullaryFunctionNullaryProcedure(NullaryFunction<?> function) {
        this.function = Validate.notNull(function, "NullaryFunction argument was null");
    }

    /**
     * {@inheritDoc}
     * {@link NullaryFunction#evaluate Evaluate} my function,
     * but ignore its returned value.
     */
    public void run() {
        function.evaluate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof NullaryFunctionNullaryProcedure)) {
            return false;
        }
        NullaryFunctionNullaryProcedure that = (NullaryFunctionNullaryProcedure) obj;
        return this.function.equals(that.function);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "NullaryFunctionNullaryProcedure".hashCode();
        hash ^= function.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "NullaryFunctionNullaryProcedure<" + function + ">";
    }

    /**
     * Adapt the given, possibly-<code>null</code>,
     * {@link NullaryFunction NullaryFunction} to the
     * {@link NullaryProcedure NullaryProcedure} interface.
     * When the given <code>NullaryFunction</code> is <code>null</code>,
     * returns <code>null</code>.
     *
     * @param function the possibly-<code>null</code>
     *        {@link NullaryFunction NullaryFunction} to adapt
     * @return a {@link NullaryProcedure NullaryProcedure} wrapping the given
     *         {@link NullaryFunction NullaryFunction}, or <code>null</code>
     *         if the given <code>NullaryFunction</code> is <code>null</code>
     */
    public static NullaryFunctionNullaryProcedure adapt(NullaryFunction<?> function) {
        return null == function ? null : new NullaryFunctionNullaryProcedure(function);
    }

}
