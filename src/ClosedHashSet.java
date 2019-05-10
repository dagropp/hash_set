/**
 * A hash-set based on closed-hashing with quadratic probing. Extends SimpleHashSet
 */
public class ClosedHashSet extends SimpleHashSet {
    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public ClosedHashSet() {
        super();
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     *
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be ignored.
     * The new table has the default values of initial capacity (16), upper load factor (0.75),
     * and lower load factor (0.25).
     *
     * @param data Values to add to the set.
     */
    public ClosedHashSet(String[] data) {
        super();
        // CODE FOR "ADD" FUNCTION.
    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set.
     */
    public boolean add(String newValue) {
        return true;
    }

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set.
     */
    public boolean contains(String searchVal) {
        return true;
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted.
     */
    public boolean delete(String toDelete) {
        return true;
    }

    /**
     * @return The number of elements currently in the set.
     */
    public int size() {
        return 0;
    }

    /**
     * Specified by: capacity in class SimpleHashSet.
     *
     * @return The current capacity (number of cells) of the table.
     */
    public int capacity() {
        return 0;
    }
}
