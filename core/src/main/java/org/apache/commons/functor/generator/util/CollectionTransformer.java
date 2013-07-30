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

package org.apache.commons.functor.generator.util;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.functor.Function;
import org.apache.commons.functor.Procedure;
import org.apache.commons.functor.generator.Generator;
import org.apache.commons.lang3.Validate;

/**
 * Transforms a generator into a collection. If a collection is not passed into
 * the constructor an ArrayList will be returned from the transform method.
 *
 * @param <E> the type of elements held in the adapted collection.
 * @since 1.0
 * @version $Revision$ $Date$
 */
public class CollectionTransformer<E, C extends Collection<? super E>> implements Function<Generator<? extends E>, C> {
    /*
     * TODO revisit this class... it could stand a more-descriptive name.  Also, it's a little
     * hard to say whether, for an instance constructed without a specific target collection,
     * #evaluate() should return a new ArrayList for each call, or continue adding to
     * a single ArrayList instance (the current behavior).
     * Perhaps this is more a documentation issue than anything.
     */

    // instance methods
    //---------------------------------------------------
    /**
     * The adapted collection has to be filled.
     */
    private final C toFill;

    // constructors
    //---------------------------------------------------
    /**
     * Create a new CollectionTransformer.
     * @param toFill Collection to fill
     */
    public CollectionTransformer(C toFill) {
        this.toFill = Validate.notNull(toFill, "toFill");
    }

    // instance methods
    //---------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public C evaluate(Generator<? extends E> generator) {
        generator.run(new Procedure<E>() {
            public void run(E obj) {
                toFill.add(obj);
            }
        });
        return toFill;
    }

    /**
     * Get a {@link CollectionTransformer} instance that simply returns any {@link Collection}.
     * @return {@link CollectionTransformer}
     */
    public static <E> CollectionTransformer<E, Collection<E>> toCollection() {
        return new CollectionTransformer<E, Collection<E>>(new ArrayList<E>());
    }
}
