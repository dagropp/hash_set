/**
 * A hash-set based on closed-hashing with quadratic probing. Extends SimpleHashSet
 */
public class ClosedHashSet extends SimpleHashSet {
    private String[] table = new String[INITIAL_CAPACITY];
    private boolean[] ignoreList = new boolean[INITIAL_CAPACITY];
    private int elementCounter = 0;

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
        for (String item : data)
            this.add(item);
    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set.
     */
    public boolean add(String newValue) {
        // Iterates over probing index, until empty slot found.
        for (int i = 0; i < this.table.length; i++) {
            int index = this.clamp(this.hash(newValue, i)); // Clamps string hash code to fit array index.
            // If index is null, assign item to index and return true.
            if (this.table[index] == null) {
                this.table[index] = newValue;
                if (this.ignoreList[index])
                    this.ignoreList[index] = false; // If index in ignore list, remove it from there.
                this.elementCounter++; // Appends element counter by 1;
                this.attemptResize(this.shouldIncrease(), true); // Checks if increase-resizing is needed.
                return true;
                // If item exists in table, do nothing and return false.
            } else if (this.table[index].equals(newValue))
                return false;
        }
        return false;
    }

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set.
     */
    public boolean contains(String searchVal) {
        // Iterates over probing index, until relevant slot found or iteration reached null slot.
        for (int i = 0; i < this.table.length; i++) {
            int index = this.clamp(this.hash(searchVal, i)); // Clamps string hash code to fit array index.
            // Null slot reached, item not in table, return false.
            if (this.ignoreList[index])
                continue;
            else if (this.table[index] == null)
                return false;
                // Index equals search val, item found, return true.
            else if (this.table[index].equals(searchVal))
                return true;
        }
        return false; // If iteration completed and no null or equal val found.
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted.
     */
    public boolean delete(String toDelete) {
        for (int i = 0; i < this.table.length; i++) {
            int index = this.clamp(this.hash(toDelete, i)); // Clamps string hash code to fit array index.
            // Null slot reached, item not in table therefore cannot be deleted, return false.
            if (this.ignoreList[index])
                continue;
            else if (this.table[index] == null)
                return false;
                // Index equals search val, item found: delete it, re-hash table and return true.
            else if (this.table[index].equals(toDelete)) {
                this.table[index] = null; // Delete index value.
                this.ignoreList[index] = true;
                this.elementCounter--; // Removes 1 from element counter.
                this.attemptResize(this.shouldDecrease(), false); // Checks if decrease-resizing is needed.
//                this.rehashTable(this.table.length); // Initialize and re-hash table in dedicated function.
                return true;
            }
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
     * @param item  String to hash.
     * @param index Index of current probing.
     * @return Hash code for String input, based on quadratic probing algorithm.
     */
    private int hash(String item, int index) {
        return item.hashCode() + ((index + index * index) / 2);
    }

    /**
     * Checks if resize is needed (increase or decrease), and if so assigns new table capacity, based on size
     * divided/multiplied in 2 (capacity must be in powers of 2, to fit quadratic probing algorithm).
     *
     * @param shouldResize Based on shouldIncrease/shouldDecrease methods from SimpleHashSet,
     *                     that checks if resizing is needed.
     * @param increase     True if increase is performed, False if decrease.
     */
    private void attemptResize(boolean shouldResize, boolean increase) {
        // Checks if resize is needed.
        if (shouldResize) {
            // New capacity based on (capacity * 2) for increase, (capacity / 2) for decrease.
            int newCapacity = increase ? this.table.length * 2 : this.table.length / 2;
            this.rehashTable(newCapacity); // Initialize and re-hash table in dedicated function.
        }
    }

    /**
     * creates new table with specified capacity, and adds and re-hashes all the previous elements to the new table.
     *
     * @param newCapacity New table's capacity.
     */
    private void rehashTable(int newCapacity) {
        String[] previousTable = this.assignTableElementsToArray(); // Assign current table elements to array.
        this.ignoreList = new boolean[newCapacity];
        this.table = new String[newCapacity]; // Creates new empty table with specified capacity.
        this.addNoDuplicatesArray(previousTable); // Adds and re-hashes all previous table's elements to new table.
    }

    /**
     * Creates an array representation of the current table elements (no duplicates, no null).
     *
     * @return Array that contains all String elements in the current table.
     */
    private String[] assignTableElementsToArray() {
        String[] tempTable = new String[this.size()]; // Assign new array the size of total elements in table.
        int index = 0;
        for (String item : this.table) // Go over all the String items of the table.
            // If index not null, assign String to the result array.
            if (item != null) {
                tempTable[index] = item;
                index++;
            }
        return tempTable;
    }

    /**
     * Adds and re-hashes previous table String elements (no duplicates, no null) to new resized table.
     *
     * @param tempTable Array representation of the previous table String elements before resizing/restart.
     */
    private void addNoDuplicatesArray(String[] tempTable) {
        for (String item : tempTable)
            this.addNoDuplicates(item);
    }

    /**
     * Adds and re-hashes previous table String element (no duplicates, no null) to new resized table.
     *
     * @param item String element in previous table before resizing/restart.
     */
    private void addNoDuplicates(String item) {
        // Iterates over probing index, until empty slot found.
        for (int i = 0; i < this.table.length; i++) {
            int index = this.clamp(this.hash(item, i)); // Clamps string hash code to fit array index.
            // If index is null, assign item to index and return true.
            if (this.table[index] == null) {
                this.table[index] = item;
                return;
            }
        }
    }

    @Override
    public String toString() {
        String result = "TABLE CONTENT:\n";
        for (int i = 0; i < this.table.length; i++)
            if (this.table[i] != null)
                result += "INDEX " + i + ": " + this.table[i] + "\n";
        return result + "\nELEMENTS: " + this.size() + " CAPACITY: " + this.capacity() +
                " LOAD FACTOR: " + (float) this.size() / (float) this.capacity();
    }
}
