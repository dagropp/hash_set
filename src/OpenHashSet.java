/**
 * A hash-set based on chaining. Extends SimpleHashSet. Note: the capacity of a chaining based hash-set is simply the
 * number of buckets (the length of the array of lists)
 */
public class OpenHashSet extends SimpleHashSet {
    /* Class members - variables */
    private LinkedListContainer[] table = new LinkedListContainer[INITIAL_CAPACITY]; // Hash table representation.
    private int elementCounter = 0; // Table's element counter. Updated on each successful add/delete.

    /* Constructors */

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

    /* Public instance Methods */

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set.
     */
    public boolean add(String newValue) {
        int index = this.clamp(this.hash(newValue)); // Clamps string hash code to fit array index.
        // If item exists in table, do nothing and return false.
        if (this.itemInList(newValue, index))
            return false;
        this.elementCounter++; // Item not in table. Appends element counter by 1.
        // Checks if increasing table is needed, if so call method and resize/re-hash table, also with new value.
        if (this.shouldIncrease()) {
            this.resize(true, newValue);
            return true;
        }
        // If index is null, creates new linked list container.
        else if (this.table[index] == null)
            this.table[index] = new LinkedListContainer(newValue);
            // Index has existing list with items that do not much new value. Adds item to list and return true.
        else
            this.table[index].addItem(newValue);
        return true;
    }

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set.
     */
    public boolean contains(String searchVal) {
        int index = this.clamp(this.hash(searchVal)); // Clamps string hash code to fit array index.
        return this.itemInList(searchVal, index); // Returns boolean from dedicated method that checks if item exists.
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted.
     */
    public boolean delete(String toDelete) {
        int index = this.clamp(this.hash(toDelete)); // Clamps string hash code to fit array index.
        if (this.table[index].findAndDelete(toDelete)) {
            // If after deletion, list bucket is empty, nullify index.
            if (this.table[index].getContainer().isEmpty())
                this.table[index] = null;
            this.elementCounter--; // Removes 1 from element counter.
            // Checks if resizing is needed (decrease), and if so decrease table with dedicated method.
            if (shouldDecrease())
                this.resize(false, toDelete);
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

    /* Protected instance Methods */

    /**
     * Reinitialize hash table based on new capacity.
     *
     * @param newCapacity New table capacity.
     */
    protected void newTable(int newCapacity) {
        this.table = new LinkedListContainer[newCapacity]; // Creates new empty table with specified capacity.
    }

    /**
     * Creates an array representation of the current table elements (no duplicates, no null).
     *
     * @return Array that contains all String elements in the current table.
     */
    protected String[] assignTableElementsToArray() {
        String[] tempTable = new String[this.size()]; // Assign new array the size of total elements in table.
        int index = 0;
        for (LinkedListContainer list : this.table) // Go over all the list buckets of the table.
            // If index not null, iterate over the linked list and assign String values to the result array.
            if (list != null)
                for (Object item : list.getContainer()) {
                    tempTable[index] = item.toString();
                    index++;
                }
        return tempTable;
    }

    /**
     * Adds String element (no duplicates, no null) to new resized table.
     *
     * @param item Element to add.
     */
    protected void addUnique(String item) {
        int index = this.clamp(this.hash(item)); // Clamps string hash code to fit array index.
        // If index is null, creates new linked list container.
        if (this.table[index] == null)
            this.table[index] = new LinkedListContainer(item);
            // Index has existing list with items that do not much new value. Adds item to list.
        else
            this.table[index].addItem(item);
    }

    /* Private instance Methods */

    /**
     * @param item String to hash.
     * @return Hash code for String input.
     */
    private int hash(String item) {
        return item.hashCode();
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
