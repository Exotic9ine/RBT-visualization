
public class Node<T extends Comparable<T>> {
    private static final boolean RED   = false;
    private static final boolean BLACK = true;
    boolean color;
    T key;
    Node<T> left;
    Node<T> right;
    Node<T> parent;

    public Node(T key, boolean color, Node<T> parent, Node<T> left, Node<T> right) {
        this.key = key;
        this.color = color;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    public T getKey() {
        return key;
    }

    public String toString() {
        return ""+key+(this.color==RED?"(R)":"B");
    }
}