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

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.functor.generator.Generator;
import org.apache.commons.functor.generator.loop.IteratorToGeneratorAdapter;

/**
 * Generator factory for each element of a "collection".
 *
 * @since 1.0
 * @version $Revision$ $Date$
 */
public final class EachElement {

    /**
     * Hidden constructor as this only is a helper class with static methods.
     */
    private EachElement() {
    }

    /**
     * Get a Generator for each element of an Iterable.
     * @param <E> the type of elements held in the input iterable.
     * @param iterable to iterate
     * @return Generator<E>
     */
    public static <E> Generator<E> from(Iterable<? extends E> iterable) {
        return iterable == null ? null : EachElement.from(iterable.iterator());
    }

    /**
     * Get a Generator for each entry of a Map.
     * @param <K> the type of keys maintained by the input map.
     * @param <V> the type of mapped values in the input map.
     * @param map to iterate
     * @return Generator
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Generator<Map.Entry<K, V>> from(Map<? extends K, ? extends V> map) {
        return map == null ? null : EachElement.from(((Map<K, V>) map).entrySet().iterator());
    }

    /**
     * Get a Generator for each element of an Object[].
     * @param <E> the type of elements held in the input array.
     * @param array to iterate
     * @return Generator
     */
    public static <E> Generator<E> from(E... array) {
        return array == null ? null : EachElement.from(Arrays.asList(array).iterator());
    }

    /**
     * Get a Generator for each element of an Iterator.
     * @param <E> the type of elements held in the input iterator.
     * @param iter to iterate
     * @return Generator
     */
    public static <E> Generator<E> from(Iterator<? extends E> iter) {
        return iter == null ? null : new IteratorToGeneratorAdapter<E>(iter);
    }
}
