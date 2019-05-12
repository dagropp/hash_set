/**
 * A superclass for implementations of hash-sets implementing the SimpleSet interface.
 */
public abstract class SimpleHashSet implements SimpleSet {
    /* Class members - constant variables */
    // Describes the higher load factor of a newly created hash set
    protected static final float DEFAULT_HIGHER_CAPACITY = 0.75f;
    // Describes the lower load factor of a newly created hash set
    protected static final float DEFAULT_LOWER_CAPACITY = 0.25f;
    // Describes the capacity of a newly created hash set.
    protected static final int INITIAL_CAPACITY = 16;
    /* Class members - variables */
    private float upperLoadFactor;
    private float lowerLoadFactor;

    /* Constructors */

    /**
     * Constructs a new hash set with the default capacities given in
     * DEFAULT_LOWER_CAPACITY and DEFAULT_HIGHER_CAPACITY.
     */
    protected SimpleHashSet() {
        this.upperLoadFactor = DEFAULT_HIGHER_CAPACITY;
        this.lowerLoadFactor = DEFAULT_LOWER_CAPACITY;
    }

    /**
     * Constructs a new hash set with capacity INITIAL_CAPACITY.
     *
     * @param upperLoadFactor the upper load factor before rehashing.
     * @param lowerLoadFactor the lower load factor before rehashing.
     */
    protected SimpleHashSet(float upperLoadFactor, float lowerLoadFactor) {
        this.upperLoadFactor = upperLoadFactor;
        this.lowerLoadFactor = lowerLoadFactor;
    }

    /* Public instance Methods */

    /**
     * @return The current capacity (number of cells) of the table.
     */
    public abstract int capacity();

    /* Protected instance Methods */

    /**
     * Clamps hashing indices to fit within the current table capacity (see the exercise description for details).
     *
     * @param index the index before clamping.
     * @return an index properly clamped.
     */
    protected int clamp(int index) {
        return index & (this.capacity() - 1);
    }

    /**
     * @return The lower load factor of the table.
     */
    protected float getLowerLoadFactor() {
        return this.lowerLoadFactor;
    }

    /**
     * @return The higher load factor of the table.
     */
    protected float getUpperLoadFactor() {
        return this.upperLoadFactor;
    }

    /**
     * Checks if table capacity should increase, if current load factor is higher than upper load factor.
     *
     * @return True if (current load factor > upper load factor): should increase. False otherwise.
     */
    protected boolean shouldIncrease() {
        return this.getCurrentLoadFactor() > this.upperLoadFactor;
    }

    /**
     * Checks if table capacity should decrease, if current load factor is lower than lower load factor.
     *
     * @return True if (current load factor < lower load factor): should decrease. False otherwise.
     */
    protected boolean shouldDecrease() {
        return this.getCurrentLoadFactor() < this.lowerLoadFactor && this.capacity() > 1;
    }

    /* Private instance Methods */

    /**
     * @return The current load factor of the table.
     */
    private float getCurrentLoadFactor() {
        return (float) this.size() / (float) this.capacity();
    }
}
