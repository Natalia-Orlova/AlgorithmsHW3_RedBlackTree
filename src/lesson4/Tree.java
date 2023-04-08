package lesson4;

public class Tree <V extends Comparable<V>>{
    private Node root;

    public boolean add(V value) {
        if (root != null) { // если есть рутовый элемент, то
            boolean result = addNode(root, value); // добавляем новую ноду относительно рута
            root = rebalance(root); // и делаем ребалансировку
            root.color = Color.BLACK; // устанавливаем для рута цвет черный
            return result;
        } else { // если не существует рутового элемента, то генерируем его
            root = new Node();
            root.color = Color.BLACK;
            root.value = value;
            return true;
        }
    }

    private boolean addNode(Node node, V value) {
        if (node.value == value) { // если уже есть такое значение, то ничего не добавляем
            return false;
        } else {
            if (node.value.compareTo(value) > 0) {
                if (node.leftChild != null) {
                    boolean result = addNode(node.leftChild, value);
                    node.leftChild = rebalance(node.leftChild);
                    return result;
                } else {
                    node.leftChild = new Node();
                    node.leftChild.color = Color.RED; // все новые элементы изначально красные
                    node.leftChild.value = value;
                    return true;
                }
            } else {
                if (node.rightChild != null) {
                    boolean result = addNode(node.rightChild, value);
                    node.rightChild = rebalance(node.rightChild);
                    return result;
                } else { node.rightChild = new Node ();
                    node.rightChild.color = Color.RED;
                    node.rightChild.value = value;
                    return true;
                }
            }
        }
    }

    private Node rebalance (Node node) {
        Node result = node;
        boolean needRebalance;
        do {
            needRebalance = false;
            // если правый ребенок красный, а левый ребенок черный, то делаем правосторонний разворот
            if (result.rightChild != null && result.rightChild.color == Color.RED &&
                    (result.leftChild == null || result.leftChild.color == Color.BLACK)) {
                needRebalance = true;
                result = rightSwap(result);
            }
            // если левый ребенок красный и его левый ребенок тоже красный, то левосторонний разворот
            if (result.leftChild != null && result.leftChild.color == Color.RED &&
                    result.leftChild.leftChild != null && result.leftChild.leftChild.color == Color.RED) {
                needRebalance = true;
                result = leftSwap(result);
            }
            // если и правый, и левый ребенок красного цвета
            if (result.leftChild != null && result.leftChild.color == Color.RED &&
                    result.rightChild != null && result.rightChild.color == Color.RED) {
                needRebalance = true;
                colorSwap(result);
            }
        }
        while (needRebalance);
        return result;
    }

    // правосторонний разворот
    private Node rightSwap (Node node) {
        Node rightChild = node.rightChild;
        Node betweenChild = rightChild.leftChild;
        rightChild.leftChild = node;
        node.rightChild = betweenChild;
        rightChild.color = node.color;
        node.color = Color.RED;
        return rightChild;
    }

    // левосторонний разворот
    private Node leftSwap (Node node) {
        Node leftChild = node.leftChild;
        Node betweenChild = leftChild.rightChild;
        leftChild.rightChild = node;
        node.leftChild = betweenChild;
        leftChild.color = node.color;
        node.color = Color.RED;
        return leftChild;
    }

    private void colorSwap (Node node) {
        node.rightChild.color = Color.BLACK;
        node.leftChild.color = Color.BLACK;
        node.color = Color.RED;
    }
    private class Node {
        private V value;
        private Color color;
        private Node leftChild;
        private Node rightChild;

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", color=" + color +
                    '}';
        }
    }

    private enum Color {
        RED, BLACK
    }

}
