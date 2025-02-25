/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class RecoveraTreeFromPreorderTraversal {

    // https://leetcode.com/problems/recover-a-tree-from-preorder-traversal/?envType=daily-question&envId=2025-02-22
    
    private String traversal;
    private int n;
    private int index;

    public TreeNode recoverFromPreorder(String traversal) {
        this.traversal = traversal;
        n = traversal.length();
        index = 0;
        return helper(0);
    }

    private TreeNode helper(int depth) {
        int numEndIndex = getNumEndIndex(index);
        int num = Integer.parseInt(traversal.substring(index, numEndIndex));
        TreeNode cur = new TreeNode(num);
        
        index = numEndIndex;

        // try to get child on left side
        int dashEndIndex = getDashEndIndex(index);
        int newDepth = dashEndIndex - index;
        if (newDepth > depth) {
            index = dashEndIndex;
            cur.left = helper(newDepth);
        }

        // try to get child on right side
        dashEndIndex = getDashEndIndex(index);
        newDepth = dashEndIndex - index;
        if (newDepth > depth) {
            index = dashEndIndex;
            cur.right = helper(newDepth);
        }

        return cur;
    }

    private int getNumEndIndex(int index) {
        for (; index < n; index++) {
            if (traversal.charAt(index) == '-') return index;
        }
        return n;
    }

    private int getDashEndIndex(int index) {
        for (; index < n; index++) {
            if (traversal.charAt(index) != '-') return index;
        }
        return n;
    }

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
