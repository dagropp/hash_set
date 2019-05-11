/**
 * A hash-set based on closed-hashing with quadratic probing. Extends SimpleHashSet
 */
public class ClosedHashSet extends SimpleHashSet {
    private String[] table = new String[INITIAL_CAPACITY];
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
        for (int i = 0; i < this.table.length; i++) {
            int index = this.clamp(this.hash(newValue, i));
            if (this.table[index] == null) {
                this.table[index] = newValue;
                this.elementCounter++;
                this.resize(this.shouldIncrease());
                return true;
            } else if (this.table[index].equals(newValue))
                return false;
        }
//        this.attemptResize(true, this.getLowerLoadFactor());
        return false;
    }

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set.
     */
    public boolean contains(String searchVal) {
        for (int i = 0; i < this.table.length; i++) {
            int index = this.clamp(this.hash(searchVal, i));
            if (this.table[index].equals(searchVal))
                return true;
            else if (this.table[index] == null)
                return false;
        }
        return false;
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

    private int hash(String item, int index) {
        return item.hashCode() + ((index + index * index) / 2);
    }

    /**
     * Creates an array representation of the current table elements (no duplicates, no null).
     *
     * @return Array that contains all String elements in the current table.
     */
    @Override
    protected String[] assignTableElementsToArray() {
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

    @Override
    protected void addNoDuplicatesArray(String[] tempTable) {
        this.elementCounter = 0;
        for (String item : tempTable) {
//            for (int i = 0; i < this.table.length; i++) {
//                int index = this.clamp(this.hash(item, i));
//                if (this.table[index] == null) {
//                    this.table[index] = item;
//                    break;
//                }
//            }
        this.add(item);
        }
    }

    @Override
    protected void newTable(int size) {
        this.table = new String[size];
    }

    private void resize(boolean shouldResize) {
        if (shouldResize) {
            int newSize = this.table.length * 2; // Which size to increase table to??
            String[] previousTable = this.assignTableElementsToArray();
            this.newTable(newSize);
            this.addNoDuplicatesArray(previousTable);
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
