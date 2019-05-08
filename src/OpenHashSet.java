/**
 * A hash-set based on chaining. Extends SimpleHashSet. Note: the capacity of a chaining based hash-set is simply the
 * number of buckets (the length of the array of lists)
 */
public class OpenHashSet extends SimpleHashSet {
    private LinkedListContainer[] table;
    private int elementCounter = 0;

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public OpenHashSet() {
        this.table = new LinkedListContainer[INITIAL_CAPACITY];
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     *
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
        this.table = new LinkedListContainer[INITIAL_CAPACITY];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be ignored.
     * The new table has the default values of initial capacity (16), upper load factor (0.75),
     * and lower load factor (0.25).
     *
     * @param data Values to add to the set.
     */
    public OpenHashSet(String[] data) {

    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set.
     */
    public boolean add(String newValue) {
        int index = this.setIndex(newValue); // Sets array index for string input with dedicated method.
        // If item exists in table, do nothing and return false.
        if (this.itemInList(newValue, index))
            return false;
            // If index is null, creates new linked list container and return true.
        else if (this.table[index] == null)
            this.table[index] = new LinkedListContainer(newValue);
            // Index has existing list with items that do not much new value. Adds item to list and return true.
        else
            this.table[index].addItem(newValue);
        this.elementCounter++; // Appends element counter by 1;
        return true;
    }


    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set.
     */
    public boolean contains(String searchVal) {
        int index = this.setIndex(searchVal); // Sets array index for string input with dedicated method.
        return this.itemInList(searchVal, index); // Returns boolean from dedicated method that checks if item exists.
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted.
     */
    public boolean delete(String toDelete) {
        int index = this.setIndex(toDelete); // Sets array index for string input with dedicated method.
        if (this.table[index].findAndDelete(toDelete)) {
            this.elementCounter--; // Removes 1 from element counter.
            return true;
        }
        return false;
    }

    /**
     * @return The number of elements currently in the set.
     */
    public int size() {
        return this.elementCounter;
    }

    /**
     * Specified by: capacity in class SimpleHashSet.
     *
     * @return The current capacity (number of cells) of the table.
     */
    public int capacity() {
        return this.table.length;
    }

    /**
     * Sets array index for String input, using the input's HashCode % size of the table.
     *
     * @param item String value to assign array index to.
     * @return Index value in table's range for the input.
     */
    private int setIndex(String item) {
        return Math.abs(item.hashCode()) % table.length;
    }

    /**
     * Checks if item exists on specific list in index.
     *
     * @param item  String value to check if exists.
     * @param index Array index where the item is supposed to be stored.
     * @return True if item exists, false if otherwise.
     */
    private boolean itemInList(String item, int index) {
        // If index not null, calls LinkedListContainer boolean dedicated method that checks if item exists there.
        if (this.table[index] != null)
            return this.table[index].contains(item);
        return false;
    }
}
