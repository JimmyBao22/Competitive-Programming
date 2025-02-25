import java.util.*;

public class UniqueBinarySearchTreesII {

	// https://leetcode.com/problems/unique-binary-search-trees-ii/
	
	public static void main(String[] args) {
		generateTrees(3);
	}
	
	
	public static List<TreeNode> generateTrees(int n) {
		List<TreeNode> c = new ArrayList<>();
		if (n == 1) {
			c.add(new TreeNode(1));
			return c;
		}
		int left = 0; int right = n+1;
		for (int i=left + 1; i < right; i++) {
			// take element i
			ArrayList<TreeNode> l = rec(left, i, i);	// all trees on left
			ArrayList<TreeNode> r = rec(i, right, i);	// all trees on right
			if (l.size() == 0) {
				for (int k=0; k<r.size(); k++) {
					TreeNode cur = new TreeNode(i);
					cur.right = r.get(k);
					c.add(cur);
				}
			}
			else if (r.size() == 0) {
				for (int k=0; k<l.size(); k++) {
					TreeNode cur = new TreeNode(i);
					cur.left = l.get(k);
					c.add(cur);
				}
			}
			else {
				for (int j=0; j<l.size(); j++) {
					for (int k=0; k<r.size(); k++) {
						TreeNode cur = new TreeNode(i);
						cur.left = l.get(j);
						cur.right = r.get(k);
						c.add(cur);
					}
				}
			}
		}
		return c;
    }

	public static ArrayList<TreeNode> rec(int left, int right, int last_took) {
		if (right - left == 2) {
			ArrayList<TreeNode> c = new ArrayList<>();
			c.add(new TreeNode(left + 1));
			return c;
		}
		ArrayList<TreeNode> c = new ArrayList<>();
		for (int i=left + 1; i < right; i++) {
			// take element i
			ArrayList<TreeNode> l = rec(left, i, i);
			ArrayList<TreeNode> r = rec(i, right, i);
			if (l.size() == 0) {
				for (int k=0; k<r.size(); k++) {
					TreeNode cur = new TreeNode(i);
					cur.right = r.get(k);
					c.add(cur);
				}
			}
			else if (r.size() == 0) {
				for (int k=0; k<l.size(); k++) {
					TreeNode cur = new TreeNode(i);
					cur.left = l.get(k);
					c.add(cur);
				}
			}
			else {
				for (int j=0; j<l.size(); j++) {
					for (int k=0; k<r.size(); k++) {
						TreeNode cur = new TreeNode(i);
						cur.left = l.get(j);
						cur.right = r.get(k);
						c.add(cur);
					}
				}
			}
		}
		
		return c;
	}
	

	 public static class TreeNode {
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