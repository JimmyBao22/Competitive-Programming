
public class MergekSortedLists {

	// https://leetcode.com/problems/merge-k-sorted-lists/
	
	public static void main(String[] args) {

	}

	public ListNode mergeKLists(ListNode[] lists) {
		int n = lists.length;
		if (n == 0) return null;
    	int minval = (int)(1e4+10);
    	int index=0;
    	for (int j=0; j<n; j++) {
    		if (lists[j] != null) {
    			if (lists[j].val < minval) {
    				index = j;
    				minval = lists[j].val;
    			}
    		}
    	}
    	ListNode cur = new ListNode(minval);
    	if (lists[index] == null) return null;
    	else lists[index] = lists[index].next;
    	cur.next = mergeKLists(lists);
    	return cur;
    }

	public class ListNode {
	      int val;
	      ListNode next;
	      ListNode() {}
	      ListNode(int val) { this.val = val; }
	      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
	  }
}