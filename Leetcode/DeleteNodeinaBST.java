class DeleteNodeinaBST {
    
    // https://leetcode.com/problems/delete-node-in-a-bst/
    
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }

        if (root.val == key) {
            // remove this node, replace it with the largest value in the left subtree
            if (root.left == null) {
                root = root.right;
            } else {
                TreeNode largest = getLargestNode(root.left);
                
                // replace this val with that val
                root.val = largest.val;

                // delete the largest node in the left subtree from the tree
                root.left = deleteNode(root.left, largest.val);
            }
        } else if (root.val < key) {
            root.right = deleteNode(root.right, key);
        } else {
            root.left = deleteNode(root.left, key);
        }

        return root;
    }

    // gets the largest node
    private TreeNode getLargestNode(TreeNode root) {
        if (root.right == null) return root;
        return getLargestNode(root.right);
    }

    // Definition for a binary tree node.
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}