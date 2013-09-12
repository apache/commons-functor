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

import org.apache.commons.functor.generator.BaseGenerator;
import org.apache.commons.functor.generator.Generator;

/**
 * Base class for generators that control execution flow, and may need to
 * stop the generation.
 *
 * @param <E> the type of elements held in this generator.
 * @since 1.0
 * @version $Revision$ $Date$
 */
public abstract class LoopGenerator<E> extends BaseGenerator<E> {

    /** A generator can wrap another generator. */
    private final Generator<? extends E> wrappedGenerator;

    /** Set to true when the generator is {@link #stop stopped}. */
    private boolean stopped = false;

    /** Create a new generator. */
    public LoopGenerator() {
        this(null);
    }

    /**
     * A generator can wrap another generator. When wrapping generators you
     * should use probably this constructor since doing so will cause the
     * {@link #stop} method to stop the wrapped generator as well.
     * @param generator Generator to wrap
     */
    public LoopGenerator(Generator<? extends E> generator) {
        this.wrappedGenerator = generator;
    }

    /**
     * Get the generator that is being wrapped.
     * @return Generator
     */
    protected Generator<? extends E> getWrappedGenerator() {
        return wrappedGenerator;
    }

    /**
     * Stop the generator. Will stop the wrapped generator if one was set.
     */
    public void stop() {
        if (wrappedGenerator != null && wrappedGenerator instanceof LoopGenerator<?>) {
            ((LoopGenerator<?>) wrappedGenerator).stop();
        }
        stopped = true;
    }

    /**
     * Check if the generator is stopped.
     * @return <code>true</code> if the generator is stopped, <code>false</code>
     * otherwise
     */
    public boolean isStopped() {
        return stopped;
    }

}
