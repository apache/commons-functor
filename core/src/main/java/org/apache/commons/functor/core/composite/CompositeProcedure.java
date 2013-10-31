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

import org.apache.commons.functor.Function;
import org.apache.commons.functor.Procedure;
import org.apache.commons.functor.adapter.ProcedureFunction;
import org.apache.commons.lang3.Validate;

/**
 * A {@link Procedure Procedure}
 * representing the composition of
 * {@link Function Functions},
 * "chaining" the output of one to the input
 * of another.  For example,
 * <pre>new CompositeProcedure(p).of(f)</pre>
 * {@link #run runs} to
 * <code>p.run(f.evaluate(obj))</code>, and
 * <pre>new CompositeProcedure(p).of(f).of(g)</pre>
 * {@link #run runs}
 * <code>p.run(f.evaluate(g.evaluate(obj)))</code>.
 * @param <A> the procedure argument type.
 * @version $Revision: 1345136 $ $Date: 2012-06-01 09:47:06 -0300 (Fri, 01 Jun 2012) $
 */
public final class CompositeProcedure<A> implements Procedure<A> {
    // attributes
    // ------------------------------------------------------------------------
    /**
     * The adapted composite procedure.
     */
    private final CompositeFunction<? super A, Object> function;

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new CompositeProcedure.
     * @param procedure final Procedure to run
     */
    public CompositeProcedure(Procedure<? super A> procedure) {
        this.function =
            new CompositeFunction<A, Object>(
                new ProcedureFunction<A, Object>(Validate.notNull(
                    procedure, "procedure must not be null")));
    }

    /**
     * Create a new CompositeProcedure.
     * @param function final CompositeFunction to run
     */
    private CompositeProcedure(CompositeFunction<? super A, Object> function) {
        this.function = function;
    }

    // modifiers
    // ------------------------------------------------------------------------
    /**
     * Fluently obtain a CompositeProcedure that runs our procedure against the result of the preceding function.
     * @param <T> the input function left argument and output procedure argument type
     * @param preceding Function
     * @return CompositeProcedure<P>
     */
    public <T> CompositeProcedure<T> of(Function<? super T, ? extends A> preceding) {
        return new CompositeProcedure<T>(function.of(preceding));
    }

    // predicate interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public void run(A obj) {
        function.evaluate(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CompositeProcedure<?>)) {
            return false;
        }
        CompositeProcedure<?> that = (CompositeProcedure<?>) obj;
        return this.function.equals(that.function);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "CompositeProcedure".hashCode();
        hash <<= 2;
        hash ^= function.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "CompositeProcedure<" + function + ">";
    }

}
