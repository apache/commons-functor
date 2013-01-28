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

import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.UnaryProcedure;
import org.apache.commons.functor.generator.BaseGenerator;
import org.apache.commons.functor.generator.Generator;

/**
 * Base class for generators that control execution flow, and may need to
 * stop the generation.
 *
 * @param <E> the type of elements held in this generator.
 * @since 1.0
 * @version $Revision:$ $Date:$
 */
public abstract class PredicatedGenerator<E> extends BaseGenerator<E> {
    public enum Behavior {
        TEST_BEFORE, TEST_AFTER;
    }

    private final Generator<? extends E> wrappedGenerator;
    private final UnaryPredicate<? super E> continuePredicate;
    private final Behavior behavior;

    /** Set to true when the generator is {@link #stop() stopped}. */
    private boolean stopped = false;

    /**
     * Create a new PredicatedGenerator instance.
     * @param wrappedGenerator
     * @param continuePredicate
     * @param behavior
     */
    protected PredicatedGenerator(Generator<? extends E> wrappedGenerator, UnaryPredicate<? super E> continuePredicate, Behavior behavior) {
        super();
        this.wrappedGenerator = wrappedGenerator;
        this.continuePredicate = continuePredicate;
        this.behavior = behavior;
    }

    /**
     * Get the generator that is being wrapped.
     * @return Generator
     */
    protected Generator<? extends E> getWrappedGenerator() {
        return wrappedGenerator;
    }

    /**
     * {@inheritDoc}
     */
    public void run(final UnaryProcedure<? super E> proc) {
        wrappedGenerator.run(new UnaryProcedure<E>() {
            public void run(E obj) {
                if (stopped) {
                    return;
                }
                if (behavior == Behavior.TEST_BEFORE && !continuePredicate.test(obj)) {
                    stop();
                    return;
                }
                proc.run(obj);
                if (behavior == Behavior.TEST_AFTER && !continuePredicate.test(obj)) {
                    stop();
                }
            }
        });
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PredicatedGenerator<?>)) {
            return false;
        }
        PredicatedGenerator<?> other = (PredicatedGenerator<?>) obj;
        return other.getWrappedGenerator().equals(getWrappedGenerator())
            && other.continuePredicate.equals(continuePredicate) && other.behavior == behavior;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = getClass().getSimpleName().hashCode();
        result <<= 2;
        Generator<?> gen = getWrappedGenerator();
        result ^= gen.hashCode();
        result <<= 2;
        result ^= continuePredicate.hashCode();
        result <<= 2;
        result ^= behavior.ordinal();
        return result;
    }

    private void stop() {
        stopped = true;
    }

}
