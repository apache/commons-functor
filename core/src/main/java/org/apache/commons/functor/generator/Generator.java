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
package org.apache.commons.functor.generator;

import java.util.Collection;

import org.apache.commons.functor.Function;
import org.apache.commons.functor.Procedure;

/**
 * The Generator interface defines a number of useful actions applying Procedures
 * to each in a series of argument Objects.
 *
 * @param <E> the type of elements held in this generator.
 * @version $Revision$ $Date$
 */
public interface Generator<E> {
    /**
     * Generators must implement this method.
     * @param proc Procedure to run
     */
    void run(Procedure<? super E> proc);

    /**
     * Transforms this generator using the passed in
     * transformer. An example transformer might turn the contents of the
     * generator into a {@link Collection} of elements.
     * @param <Z> the returned value type of the input {@link Function}
     * @param transformer Function to apply to this
     * @return transformation result
     */
    <Z> Z to(Function<Generator<? extends E>, ? extends Z> transformer);

    /**
     * Same as to(new CollectionTransformer(collection)).
     * @param <C> the returned collection type
     * @param collection Collection to which my elements should be added
     * @return <code>collection</code>
     */
    <C extends Collection<? super E>> C to(C collection);

    /**
     * Same as to(new CollectionTransformer()).
     * @return Collection
     */
    Collection<? super E> toCollection();
}
