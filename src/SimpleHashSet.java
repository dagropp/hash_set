/**
 * A superclass for implementations of hash-sets implementing the SimpleSet interface.
 */
public abstract class SimpleHashSet implements SimpleSet {
    /* Class members - constant variables */
    // Describes the higher load factor of a newly created hash set
    protected static final float DEFAULT_HIGHER_CAPACITY = 0.75f;
    // Describes the lower load factor of a newly created hash set
    protected static final float DEFAULT_LOWER_CAPACITY = 0.25f;
    protected static final int INITIAL_CAPACITY = 16; // Describes the capacity of a newly created hash set.
    private static final int MIN_CAPACITY = 1; // Hash set minimum capacity.
    /* Class members - variables */
    private float upperLoadFactor; // Higher load factor of a this hash set.
    private float lowerLoadFactor; // Lower load factor of a this hash set.

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
        return this.getCurrentLoadFactor() > this.getUpperLoadFactor();
    }

    /**
     * Checks if table capacity should decrease, if current load factor is lower than lower load factor.
     *
     * @return True if (current load factor < lower load factor): should decrease. False otherwise.
     */
    protected boolean shouldDecrease() {
        return this.getCurrentLoadFactor() < this.getLowerLoadFactor() && this.capacity() > MIN_CAPACITY;
    }

    /**
     * Attempts to resize the table. Assigns new table capacity, based on size divided/multiplied in 2 (capacity must
     * be in powers of 2), creates new table with specified capacity, and adds and re-hashes all the previous elements
     * to the new table.
     *
     * @param increase True if increase is performed, False if decrease.
     * @param lastItem Item that was added/deleted before table resize.
     */
    protected void resize(boolean increase, String lastItem) {
        // New capacity based on (capacity * 2) for increase, (capacity / 2) for decrease.
        int newCapacity = increase ? this.capacity() * 2 : this.capacity() / 2;
        String[] previousTable = this.assignTableElementsToArray(); // Assign current table elements to array.
        // If increase, assign item that was added before resize to last place on the array.
        if (increase) previousTable[previousTable.length - 1] = lastItem;
        this.newTable(newCapacity); // Creates new empty table with specified capacity.
        this.addUniqueArray(previousTable); // Adds and re-hash all previous table's elements to new table.
    }

    /**
     * Reinitialize hash table based on new capacity.
     *
     * @param newCapacity New table capacity.
     */
    protected abstract void newTable(int newCapacity);

    /**
     * Creates an array representation of the current table elements (no duplicates, no null).
     *
     * @return Array that contains all String elements in the current table.
     */
    protected abstract String[] assignTableElementsToArray();

    /**
     * Adds String element (no duplicates, no null) to new resized table.
     *
     * @param item Element to add.
     */
    protected abstract void addUnique(String item);


    /* Private instance Methods */

    /**
     * @return The current load factor of the table.
     */
    private float getCurrentLoadFactor() {
        return (float) this.size() / (float) this.capacity();
    }

    /**
     * Adds and re-hashes previous table String elements (no duplicates, no null) to new resized table.
     *
     * @param tempTable Array representation of the previous table String elements before resizing.
     */
    private void addUniqueArray(String[] tempTable) {
        for (String item : tempTable)
            this.addUnique(item);
    }
}
