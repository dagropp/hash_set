import java.util.LinkedList;

public class LinkedListContainer {
    private LinkedList list;

    public LinkedListContainer(String item) {
        this.list = new LinkedList<String>();
        this.addItem(item);
    }

    public LinkedList getList() {
        return this.list;
    }

    public void addItem(String item) {
        this.list.add(item);
    }

    public boolean contains(String item) {
        for (Object listArticle : this.list)
            if (listArticle.equals(item))
                return true;
        return false;
    }

    public boolean findAndDelete(String item) {
        for (Object listArticle : this.list)
            if (listArticle.equals(item)) {
                this.list.remove(listArticle);
                return true;
            }
        return false;
    }

    @Override
    public int hashCode() {
        return this.list.getFirst().hashCode();
    }

    @Override
    public String toString() {
        String result = "";
        for (Object item : list)
            result += item + ", ";
        return result.substring(0, result.length() - 2);
    }
}
