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
import org.apache.commons.functor.generator.util.CollectionTransformer;

/**
 * Base class for generators. Adds support for all of the Algorithms to
 * each subclass.
 *
 * @param <E> the type of elements held in this generator.
 * @since 1.0
 * @version $Revision$ $Date$
 */
public abstract class BaseGenerator<E> implements Generator<E> {

    /** Create a new generator. */
    public BaseGenerator() {
        super();
    }

    /**
     * {@inheritDoc}
     * Transforms this generator using the passed in
     * Function. An example function might turn the contents of the
     * generator into a {@link Collection} of elements.
     */
    public final <T> T to(Function<Generator<? extends E>, ? extends T> transformer) {
        return transformer.evaluate(this);
    }

    /**
     * {@inheritDoc}
     * Same as to(new CollectionTransformer(collection)).
     */
    public final <C extends Collection<? super E>> C to(C collection) {
        return to(new CollectionTransformer<E, C>(collection));
    }

    /**
     * {@inheritDoc}
     * Same as to(new CollectionTransformer()).
     */
    public final Collection<E> toCollection() {
        return to(CollectionTransformer.<E> toCollection());
    }
}
