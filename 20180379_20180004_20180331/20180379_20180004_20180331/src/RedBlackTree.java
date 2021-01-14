
public class RedBlackTree<T extends Comparable<T>> {

    private Node<T> RootNode;

    private static final boolean RED   = false;
    private static final boolean BLACK = true;


    public RedBlackTree() {
        RootNode=null;
    }

    private Node<T> parentOf(Node<T> node) {
        return node!=null ? node.parent : null;
    }
    private boolean colorOf(Node<T> node) {
        return node!=null ? node.color : BLACK;
    }
    private boolean isRed(Node<T> node) {
        return ((node!=null)&&(node.color==RED)) ? true : false;
    }
    private boolean isBlack(Node<T> node) {
        return !isRed(node);
    }
    private void setBlack(Node<T> node) {
        if (node!=null)
            node.color = BLACK;
    }
    private void setRed(Node<T> node) {
        if (node!=null)
            node.color = RED;
    }
    private void setParent(Node<T> node, Node<T> parent) {
        if (node!=null)
            node.parent = parent;
    }
    private void setColor(Node<T> node, boolean color) {
        if (node!=null)
            node.color = color;
    }

    private void preOrder(Node<T> tree) {
        if(tree != null) {
            System.out.print(tree.key+" ");
            preOrder(tree.left);
            preOrder(tree.right);
        }
    }

    public void preOrder() {
        preOrder(RootNode);
    }

    public Node<T> searchHelper(Node<T> x, T key) {
        if (x==null)
            return x;

        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return searchHelper(x.left, key);
        else if (cmp > 0)
            return searchHelper(x.right, key);
        else
            return x;
    }

    public Node<T> search(T key) {
        return searchHelper(RootNode, key);
    }

    private void inOrder(Node<T> tree) {
        if(tree != null) {
            inOrder(tree.left);
            System.out.print(tree.key+" ");
            inOrder(tree.right);
        }
    }

    public void inOrder() {
        inOrder(RootNode);
    }


    public int Counter = 0;
    public int Counter2 = 0;
    //![0]wrap
    public void inOrderDo() {
        Counter = 0;
        Counter2 = 0;
        inOrderDo(RootNode);
    }

    public void SetInOrderDo(inOrderDo ido){
        mInOrderDo = ido;
    }

    public interface inOrderDo{
        void dothis(Node node);
    }
    private inOrderDo mInOrderDo;

    private void inOrderDo(Node<T> node) {
        if(node != null) {
            Counter2+=1;
            inOrderDo(node.left);
            mInOrderDo.dothis(node);
            Counter+=1;
            inOrderDo(node.right);
            Counter2-=1;
        }
    }



    private Node<T> minimumHelper(Node<T> tree) {
        if (tree == null)
            return null;

        while(tree.left != null)
            tree = tree.left;
        return tree;
    }

    public T minimum() {
        Node<T> Node = minimumHelper(RootNode);
        if (Node != null)
        {
            return Node.key;
        }

        return null;
    }


    private Node<T> maximumHelper(Node<T> tree) {
        if (tree == null)
            return null;

        while(tree.right != null)
            tree = tree.right;
        return tree;
    }

    public T maximum() {
        Node<T> Node = maximumHelper(RootNode);
        if (Node != null)
            return Node.key;

        return null;
    }




    private void leftRotate(Node<T> x) {

        Node<T> y = x.right;


        x.right = y.left;
        if (y.left != null)
            y.left.parent = x;

        y.parent = x.parent;

        if (x.parent == null) {
            this.RootNode = y;
        } else {
            if (x.parent.left == x)
                x.parent.left = y;
            else
                x.parent.right = y;
        }

        y.left = x;
        x.parent = y;
    }


    public void rightRotate(Node<T> y) {

        Node<T> x = y.left;

        y.left = x.right;
        if (x.right != null)
            x.right.parent = y;


        x.parent = y.parent;

        if (y.parent == null) {
            this.RootNode = x;
        } else {
            if (y == y.parent.right)
                y.parent.right = x;
            else
                y.parent.left = x;
        }


        x.right = y;
        y.parent = x;
    }

    private void insertFixUp(Node<T> node) {
        Node<T> parent, grandParent;

        //If "the parent node exists, and the color of the parent node is red”
        while (((parent = parentOf(node))!=null) && isRed(parent)) {
            grandParent = parentOf(parent);

            //If the "parent node" is the left child of the "grandfather node"”
            if (parent == grandParent.left) {
                // Case 1: Uncle node is red
                Node<T> uncle = grandParent.right;
                if ((uncle!=null) && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(grandParent);
                    node = grandParent;
                    continue;
                }

                // Case 2:Uncle is black, and the current node is the right child
                if (parent.right == node) {
                    Node<T> tmp;
                    leftRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3:Uncle is black, and the current node is the left child
                setBlack(parent);
                setRed(grandParent);
                rightRotate(grandParent);

            }
            else { //If "the parent of z" is the right child of the grandparent of z”
                // Case 1: Uncle node is red
                Node<T> uncle = grandParent.left;
                if ((uncle!=null) && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(grandParent);
                    node = grandParent;
                    continue;
                }

                // Case 2: Uncle is black, and the current node is the left child
                if (parent.left == node) {
                    Node<T> TempNode;
                    rightRotate(parent);
                    TempNode = parent;
                    parent = node;
                    node = TempNode;
                }

                // Case 3: Uncle is black, and the current node is the right child
                setBlack(parent);
                setRed(grandParent);
                leftRotate(grandParent);
            }
        }

        //Set the root node to black
        setBlack(this.RootNode);
    }


    private void insert(Node<T> node) {
        int cmp;
        Node<T> y = null;
        Node<T> x = this.RootNode;


        while (x != null) {
            y = x;
            cmp = node.key.compareTo(x.key);
            if (cmp < 0)
                x = x.left;
            else
                x = x.right;
        }

        node.parent = y;
        if (y!=null) {
            cmp = node.key.compareTo(y.key);
            if (cmp < 0)
                y.left = node;
            else
                y.right = node;
        } else {
            this.RootNode = node;
        }

        //Set the color of the node to red
        node.color = RED;


        insertFixUp(node);
    }


    public void insert(T key) {
        Node<T> node=new Node<T>(key,BLACK,null,null,null);

        //If the new node fails, it returns.
        if (node != null)
            insert(node);
    }



    private void DeleteFixUp(Node<T> node, Node<T> parent) {
        Node<T> other;

        while ((node==null || isBlack(node)) && (node != this.RootNode)) {
            if (parent.left == node) {
                other = parent.right;
                if (isRed(other)) {
                    // Case 1: brother is red
                    setBlack(other);
                    setRed(parent);
                    leftRotate(parent);
                    other = parent.right;
                }

                if ((other.left==null || isBlack(other.left)) &&
                        (other.right==null || isBlack(other.right))) {
                    // Case 2: brother is black, with two children that are also black
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                } else {

                    if (other.right==null || isBlack(other.right)) {
                        // Case 3: The brother is black, and the left child of the brother is red and the right child is black.
                        setBlack(other.left);
                        setRed(other);
                        rightRotate(other);
                        other = parent.right;
                    }
                    // Case 4: The brother is black and the right child of it is red, and the left child is of any color.
                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack(other.right);
                    leftRotate(parent);
                    node = this.RootNode;
                    break;
                }
            } else {

                other = parent.left;
                if (isRed(other)) {
                    // Case 1:  brother  is red
                    setBlack(other);
                    setRed(parent);
                    rightRotate(parent);
                    other = parent.left;
                }

                if ((other.left==null || isBlack(other.left)) &&
                        (other.right==null || isBlack(other.right))) {
                    // Case 2:  brother is black, with two children are also black
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                } else {

                    if (other.left==null || isBlack(other.left)) {
                        // Case 3: The brother is black, and the left child of it is red and the right child is black.
                        setBlack(other.right);
                        setRed(other);
                        leftRotate(other);
                        other = parent.left;
                    }

                    // Case 4: The brother is black and the right child of it is red, and the left child is of any color.
                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack(other.left);
                    rightRotate(parent);
                    node = this.RootNode;
                    break;
                }
            }
        }

        if (node!=null)
            setBlack(node);
    }


    private void delete(Node<T> node) {
        Node<T> child, parent;
        boolean color;

        //When the left and right children of the deleted node are not empty.
        if ( (node.left!=null) && (node.right!=null) ) {
            // The successor node of the deleted node.
            // Use it to replace the "deleted node" position, and then remove the "deleted node".
            Node<T> replace = node;

            //Get successor nodes
            replace = replace.right;
            while (replace.left != null)
                replace = replace.left;


            if (parentOf(node)!=null) {
                if (parentOf(node).left == node)
                    parentOf(node).left = replace;
                else
                    parentOf(node).right = replace;
            } else {

                this.RootNode = replace;
            }

            // The child is the right child of the "replacement node" and the node that needs to be "adjusted"
            // There must be no left child in "replacement node"! Because it is a successor node.
            child = replace.right;
            parent = parentOf(replace);
            // Save the color of "Replace Node"
            color = colorOf(replace);

            //
            //"Deleted node" is "the parent node of its successor node"
            if (parent == node) {
                parent = replace;
            } else {     //child is not empty
                if (child!=null)
                    setParent(child, parent);
                parent.left = child;

                replace.right = node.right;
                setParent(node.right, replace);
            }

            replace.parent = node.parent;
            replace.color = node.color;
            replace.left = node.left;
            node.left.parent = replace;

            if (color == BLACK)
                DeleteFixUp(child, parent);

            node = null;
            return ;
        }

        if (node.left !=null) {
            child = node.left;
        } else {
            child = node.right;
        }

        parent = node.parent;

        //Save the color of "Replace Node"
        color = node.color;

        if (child!=null)
            child.parent = parent;

        // "node" is not the root node
        if (parent!=null) {
            if (parent.left == node)
                parent.left = child;
            else
                parent.right = child;
        } else {
            this.RootNode = child;
        }

        if (color == BLACK)
            DeleteFixUp(child, parent);
        node = null;
    }


    public void delete(T key) {
        Node<T> node;

        if ((node = search(key)) != null)
            delete(node);
    }


    private void destroy(Node<T> tree) {
        if (tree==null)
            return ;

        if (tree.left != null)
            destroy(tree.left);
        if (tree.right != null)
            destroy(tree.right);

        tree=null;
    }

    public void clear() {
        destroy(RootNode);
        RootNode = null;
    }

    private void printHelper(Node<T> tree, T key, int direction) {

        if(tree != null) {

            if(direction==0)
                System.out.printf("%2d(B) is root\n", tree.key);
            else
                System.out.printf("%2d(%s) is %2d's %6s child\n", tree.key, isRed(tree)?"R":"B", key, direction==1?"right" : "left");

            printHelper(tree.left, tree.key, -1);
            printHelper(tree.right,tree.key,  1);
        }
    }

    public void print() {
        if (RootNode != null)
            printHelper(RootNode, RootNode.key, 0);
    }
}