package org.apache.commons.functor.range;

import java.util.Collection;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Abstract {@link Range}
 * 
 * @param <T>
 * @param <S>
 */
public abstract class AbstractRange<T extends Comparable<?>, S extends Comparable<?>> implements Range<T, S> {

    /**
     * Left limit.
     */
    protected final Endpoint<T> leftEndpoint;

    /**
     * Right limit.
     */
    protected final Endpoint<T> rightEndpoint;

    /**
     * Increment step.
     */
    protected final S step;

    /**
     * Create a new {@link AbstractRange}.
     * 
     * @param leftEndpoint
     * @param rightEndpoint
     * @param step
     */
    protected AbstractRange(Endpoint<T> leftEndpoint, Endpoint<T> rightEndpoint, S step) {
        super();
        this.leftEndpoint = Validate.notNull(leftEndpoint, "Left Endpoint argument must not be null");
        this.rightEndpoint = Validate.notNull(rightEndpoint, "Right Endpoint argument must not be null");
        this.step = Validate.notNull(step, "step must not be null");
    }

    /**
     * {@inheritDoc}
     */
    public Endpoint<T> getLeftEndpoint() {
        return leftEndpoint;
    }

    /**
     * {@inheritDoc}
     */
    public Endpoint<T> getRightEndpoint() {
        return rightEndpoint;
    }

    /**
     * {@inheritDoc}
     */
    public S getStep() {
        return step;
    }

    /**
     * {@inheritDoc}
     */
    public boolean containsAll(Collection<T> col) {
        if (col == null || col.isEmpty()) {
            return false;
        }
        for (T t : col) {
            if (!contains(t)) {
                return false;
            }
        }
        return true;
    }

    // iterable, iterator methods
    // ---------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }

    // object methods
    // ---------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final String pattern = "%s<%s, %s, %s>";
        return String.format(pattern, getClass().getSimpleName(), leftEndpoint.toLeftString(),
            rightEndpoint.toRightString(), step);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractRange<?, ?>)) {
            return false;
        }
        final Range<?, ?> that = (Range<?, ?>) obj;
        return new EqualsBuilder().append(getLeftEndpoint(), that.getLeftEndpoint())
            .append(getRightEndpoint(), that.getRightEndpoint()).append(getStep(), that.getStep()).build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = getClass().getName().hashCode();
        hash <<= 2;
        hash ^= this.leftEndpoint.hashCode();
        hash <<= 2;
        hash ^= this.rightEndpoint.hashCode();
        hash <<= 2;
        hash ^= this.step.hashCode();
        return hash;
    }
}
