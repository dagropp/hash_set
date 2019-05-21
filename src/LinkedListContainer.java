import java.util.LinkedList;

/**
 * A wrapper class that holds LinkedList<String> and delegates methods to it, to have an array of that class instead
 * of LinkedList[] array (which is illegal in Java).
 */
public class LinkedListContainer {
    /* Class members - variables */
    private LinkedList<String> container;

    /* Constructors */

    /**
     * Constructor for LinkedListContainer object, that creates a new Linked List that holds 1 item,
     * and will expand with later additions.
     *
     * @param item String to add to container.
     */
    public LinkedListContainer(String item) {
        this.container = new LinkedList<>();
        this.addItem(item);
    }

    /* Public instance Methods */

    /**
     * @return The Linked List object.
     */
    public LinkedList getContainer() {
        return this.container;
    }

    /**
     * Adds item to container.
     *
     * @param item String to add to container.
     */
    public void addItem(String item) {
        this.container.add(item);
    }

    /**
     * Checks if String item already exits in container (based on value).
     *
     * @param item String to check if is in container.
     * @return True if String already in container, false otherwise.
     */
    public boolean contains(String item) {
        // Goes over the container items, and if value is equal to any, returns true.
        for (Object containerItem : this.container)
            if (containerItem.equals(item))
                return true;
        return false;
    }

    /**
     * Attempts to find item doomed to be deleted. If found, removes item.
     *
     * @param item String to remove from container.
     * @return True if item found and removed, false if otherwise.
     */
    public boolean findAndDelete(String item) {
        // Iterates over container to find specified item.
        for (String containerItem : this.container)
            if (containerItem.equals(item))
                // If found removes item with LinkedList method that returns boolean value.
                return this.container.remove(containerItem);
        return false; // If item not found.
    }
}
