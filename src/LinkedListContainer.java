import java.util.LinkedList;

/**
 * A wrapper class that holds LinkedList<String> and delegates methods to it, to have an array of that class instead
 * of LinkedList[] array (which is illegal in Java).
 */
public class LinkedListContainer {
    /* Class members - variables */
    private LinkedList<String> list;

    /* Constructors */

    /**
     * Constructor for LinkedListContainer object, that creates a new Linked List that holds 1 item,
     * and will expand with later additions.
     *
     * @param item String to add to list.
     */
    public LinkedListContainer(String item) {
        this.list = new LinkedList<>();
        this.addItem(item);
    }

    /* Public instance Methods */

    /**
     * @return The Linked List object.
     */
    public LinkedList getList() {
        return this.list;
    }

    /**
     * Adds item to list.
     *
     * @param item String to add to list.
     */
    public void addItem(String item) {
        this.list.add(item);
    }

    /**
     * Checks if String item already exits in list (based on value).
     *
     * @param item String to check if is in list.
     * @return True if String already in list, false otherwise.
     */
    public boolean contains(String item) {
        // Goes over the list items, and if value is equal to any, returns true.
//        return this.list.contains(item); // -- slower
        for (Object listItem : this.list)
            if (listItem.equals(item))
                return true;
        return false;
    }

    /**
     * Attempts to find item doomed to be deleted. If found, removes item.
     *
     * @param item String to remove from list.
     * @return True if item found and removed, false if otherwise.
     */
    public boolean findAndDelete(String item) {
        // Iterates over list to find specified item.
        for (String listArticle : this.list)
            if (listArticle.equals(item))
                // If found removes item with LinkedList method that returns boolean value.
                return this.list.remove(listArticle);
        return false; // If item not found.
    }


    @Override
    public String toString() {
        String result = "";
        int num = 0;
        for (Object item : list) {
            num++;
            result += " (" + num + ") " + item;
        }
        return result + " / TOTAL: " + num;
    }
}
