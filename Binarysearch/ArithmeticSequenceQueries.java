import java.util.*;

public class ArithmeticSequenceQueries {

	public static void main(String[] args) {

	}
	
	// https://binarysearch.com/problems/Arithmetic-Sequence-Queries
	
	public int solve(int[] nums, int[][] queries) {
		if (nums.length == 0) return 0;
		int n = nums.length; int m =queries.length;
        int[] diff = new int[n-1];
        for (int i=0; i<n-1; i++) {
        	diff[i] = nums[i+1] - nums[i];
        }
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int last = 0;
        for (int i=1; i<n-1; i++) {
        	if (diff[i] == diff[last]) {
        		
        	}
        	else {
        		map.put(last, i);
        		last = i;
        	}
        }
        map.put(last,n-1);
        
        int count=0;
        for (int i=0; i<m; i++) {
        	int l = queries[i][0];
        	int r = queries[i][1];
        	Integer start = map.floorKey(l);
        	if (start == null) continue;
        	int end = map.get(start);
        	if (end >= r) count++;
        }
        
        return count;
    }
}
