public class BST<Key extends Comparable<Key>, Value> {

    public TreeNode root;

    // Return value at given key or null if key is not in BST
    public Value get(Key key) {
        TreeNode pointer = root;

        while (pointer != null) {
            int cmp = key.compareTo(pointer.key);
            if (cmp < 0) {
                pointer = pointer.left;
            } else if (cmp > 0) {
                pointer = pointer.right;
            } else {
                return pointer.val;
            }
        }
        return null;
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private TreeNode put(TreeNode pointer, Key key, Value val) {
        if (pointer == null) {
            return new TreeNode(key, val);
        }

        int cmp = key.compareTo(pointer.key);
        if (cmp < 0) {
            pointer.left = put(pointer.left, key, val);
        } else if (cmp > 0) {
            pointer.right = put(pointer.right, key, val);
        } else {
            pointer.val = val;
        }
        return pointer;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    // Hibbard deletion (not optimal for tree structure with large number of operations)
    private TreeNode delete(TreeNode pointer, Key key) {
        if (pointer == null) return null;

        int cmp = key.compareTo(pointer.key);
        if (cmp < 0) {
            pointer.left = delete(pointer.left, key);
        } else if (cmp > 0) {
            pointer.right = delete(pointer.right, key);
        } else {
            // If node to be deleted only has 0 or 1 child(ren) use simple return of existing TreeNode
            if (pointer.right == null) return pointer.left;
            if (pointer.left == null) return pointer.right;

            // When target has 2 child branches, replace with successor (min of right branch)
            TreeNode temp = pointer;
            pointer = min(pointer.right);
            pointer.right = deleteMin(temp.right);
            pointer.left = temp.left;
        }
        return pointer;
    }

    public void deleteMin() {
        TreeNode pointer = root;
        while (pointer.left != null) {
            pointer = pointer.left;
        }
        pointer = null;
    }

    // Used in conjunction with delete() to return right side of successor node
    private TreeNode deleteMin(TreeNode pointer) {
        if (pointer.left == null) {
            return null;
        }
        pointer.left = deleteMin(pointer.left);
        return pointer;
    }

    public Iterable<Key> iterator() {
        return null;
    }

    public Key min() {
        TreeNode pointer = root;
        while (pointer.left != null) {
            pointer = pointer.left;
        }
        return pointer.key;
    }

    // Used in conjunction with delete() to determine min of larger keys
    private TreeNode min(TreeNode pointer) {
        while (pointer.left != null) {
            pointer = pointer.left;
        }
        return pointer;
    }

    public Key max() {
        TreeNode pointer = root;
        while (pointer.right != null) {
            pointer = pointer.right;
        }
        return pointer.key;
    }

    private class TreeNode {
        private Key key;
        private Value val;
        private TreeNode left, right;

        public TreeNode(Key key, Value val) {
            this.key = key;
            this.val = val;
        }
    }

    public static void main(String[] args) {
        BST<Integer, String> testTree = new BST<>();
        testTree.put(1, "A");
        System.out.println(testTree.get(1));
        testTree.put(2, "B");
        testTree.put(3, "C");
        testTree.put(1, "D");
        System.out.println(testTree.get(1));
        System.out.println("Min: " + Integer.toString(testTree.min()));
        System.out.println("Max: " + Integer.toString(testTree.max()));
        testTree.delete(1);
        System.out.println(testTree.get(1));
        System.out.println("New min: " + Integer.toString(testTree.min()));
    }
}
