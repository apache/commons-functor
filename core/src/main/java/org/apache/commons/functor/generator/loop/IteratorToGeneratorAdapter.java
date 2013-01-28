/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.functor.generator.loop;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.functor.UnaryFunction;
import org.apache.commons.functor.UnaryProcedure;
import org.apache.commons.functor.generator.BaseGenerator;
import org.apache.commons.functor.generator.Generator;
import org.apache.commons.lang3.Validate;

/**
 * Adapts an {@link Iterator} to the {@link PredicatedGenerator} interface.
 *
 * @param <E> the type of elements held in this generator.
 * @since 1.0
 * @version $Revision$ $Date$
 */
public final class IteratorToGeneratorAdapter<E> extends BaseGenerator<E> {
    // instance variables
    //-----------------------------------------------------

    /**
     * The adapted iterator.
     */
    private final Iterator<? extends E> iter;

    // constructors
    //-----------------------------------------------------
    /**
     * Create a new IteratorToGeneratorAdapter.
     * @param iter Iterator to adapt
     */
    public IteratorToGeneratorAdapter(Iterator<? extends E> iter) {
        this.iter = Validate.notNull(iter, "Iterator argument was null");
    }

    // instance methods
    //-----------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public void run(UnaryProcedure<? super E> proc) {
        while (iter.hasNext()) {
            proc.run(iter.next());
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
        if (!(obj instanceof IteratorToGeneratorAdapter<?>)) {
            return false;
        }
        IteratorToGeneratorAdapter<?> that = (IteratorToGeneratorAdapter<?>) obj;
        return this.iter.equals(that.iter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "IteratorToGeneratorAdapater".hashCode();
        hash <<= 2;
        hash ^= iter.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "IteratorToGeneratorAdapter<" + iter + ">";
    }

    // static methods
    //-----------------------------------------------------
    /**
     * Adapt an Iterator to the Generator interface.
     *
     * @param <E> the type of elements held in this generator.
     * @param iter to adapt
     * @return IteratorToGeneratorAdapter
     */
    public static <E> IteratorToGeneratorAdapter<E> adapt(Iterator<? extends E> iter) {
        return null == iter ? null : new IteratorToGeneratorAdapter<E>(iter);
    }

}
