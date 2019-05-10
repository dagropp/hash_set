/**
 * A hash-set based on chaining. Extends SimpleHashSet. Note: the capacity of a chaining based hash-set is simply the
 * number of buckets (the length of the array of lists)
 */
public class OpenHashSet extends SimpleHashSet {
    private LinkedListContainer[] table = new LinkedListContainer[INITIAL_CAPACITY];
    private int elementCounter = 0;
    private int resizeCounter = 0;

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public OpenHashSet() {
        super();
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     *
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be ignored.
     * The new table has the default values of initial capacity (16), upper load factor (0.75),
     * and lower load factor (0.25).
     *
     * @param data Values to add to the set.
     */
    public OpenHashSet(String[] data) {
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
        int index = this.setIndex(newValue); // Sets array index for string input with dedicated method.
        // If item exists in table, do nothing and return false.
        if (this.itemInList(newValue, index))
            return false;
            // If index is null, creates new linked list container.
        else if (this.table[index] == null)
            this.table[index] = new LinkedListContainer(newValue);
            // Index has existing list with items that do not much new value. Adds item to list and return true.
        else
            this.table[index].addItem(newValue);
        this.elementCounter++; // Appends element counter by 1;
        // Checks if resizing is needed (increase), and if so increase table with dedicated method.
        this.attemptResize(this.shouldIncrease(), this.getLowerLoadFactor());
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
            // If after deletion, list bucket is empty, nullify index.
            if (this.table[index].getList().isEmpty())
                this.table[index] = null;
            this.elementCounter--; // Removes 1 from element counter.
            // Checks if resizing is needed (decrease), and if so decrease table with dedicated method.
            this.attemptResize(this.shouldDecrease(), this.getUpperLoadFactor());
            return true;
        }
        return false;
    }

    /**
     * Specified by: capacity in class SimpleHashSet
     *
     * @return The current capacity (number of cells) of the table.
     */
    public int capacity() {
        return this.table.length;
    }

    /**
     * @return The number of elements currently in the set.
     */
    public int size() {
        return this.elementCounter;
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

    /**
     * Attempts to resize the table. Checks if resize is needed (increase or decrease), and if so creates new table,
     * its capacity based on (elements number / load factor), and adds all the previous elements to the new table.
     *
     * @param shouldResize Based on shouldIncrease/shouldDecrease methods from SimpleHashSet,
     *                     that checks if resizing is needed.
     * @param loadFactor   Which load factor to base new capacity by:
     *                     Lower load factor for increasing size, Upper load factor for decreasing size.
     */
    private void attemptResize(boolean shouldResize, float loadFactor) {
        // Checks if resize is needed, based on shouldIncrease/shouldDecrease methods from SimpleHashSet.
        if (shouldResize) {
            this.resizeCounter++; // !!!DELETE LATER!!!
            int newCapacity = Math.round(this.size() / loadFactor); // New capacity based on (size / load factor).
            String[] tempTable = this.assignTableElementsToArray(); // Assign table elements to array.
            this.table = new LinkedListContainer[newCapacity]; // Recreate empty hash table.
            this.addOldTableToNewTable(tempTable); // Adds previous table elements to new table.
        }
    }

    /**
     * Creates an array representation of the current table elements (no duplicates, no null).
     *
     * @return Array that contains all String elements in the current table.
     */
    private String[] assignTableElementsToArray() {
        String[] tempTable = new String[this.size()]; // Assign new array the size of total elements in table.
        int index = 0;
        for (LinkedListContainer list : this.table) // Go over all the list buckets of the table.
            // If index not null, iterate over the linked list and assign String values to the result array.
            if (list != null)
                for (Object item : list.getList()) {
                    tempTable[index] = item.toString();
                    index++;
                }
        return tempTable;
    }

    /**
     * Adds previous table String elements (no duplicates, no null) to new resized table.
     *
     * @param tempTable Array representation of the previous table String elements before resizing.
     */
    private void addOldTableToNewTable(String[] tempTable) {
        for (String item : tempTable) {
            int index = this.setIndex(item); // Sets array index for string input with dedicated method.
            // If index is null, creates new linked list container.
            if (this.table[index] == null)
                this.table[index] = new LinkedListContainer(item);
                // Index has existing list with items that do not much new value. Adds item to list.
            else
                this.table[index].addItem(item);
        }
    }

    @Override
    public String toString() {
        String result = "Hash set content (" + this.size() + " elements):\n";
        for (int i = 0; i < this.table.length; i++)
            if (this.table[i] != null)
                result += "Index " + i + ": " + this.table[i] + "\n";
        return result + "\nTOTAL CELLS: " + this.capacity() + " / TOTAL ELEMENTS: " + this.size() +
                " / LOAD FACTOR: " + (float) this.size() / (float) this.capacity() + " / TOTAL RESIZINGS: " + this.resizeCounter;
    }
}
